package eu.bindworks.keycloak.smsotp.services.gosms;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class GoSMSServiceFactory implements SmsServiceFactory {
    private static final Logger logger = Logger.getLogger(GoSMSServiceFactory.class);
    private static final Metadata metadata = new Metadata("login", "password", "channel", "-");

    private final HashMap<String, Session> sessions = new HashMap<>();

    @Override
    public SmsService create(KeycloakSession keycloakSession, SmsServiceConfig config) {
        GoSMSRestService restService = new GoSMSRestService(keycloakSession);
        Session session = authorize(restService, config.getSmsServiceLogin(), config.getSmsServicePassword());
        return new GoSMSService(restService, session.accessToken, config.getSmsServiceParameter1());
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    private Session authorize(GoSMSRestService restService, String login, String pw) {
        synchronized (sessions) {
            Session session = sessions.get(login);

            if (session != null
                    && session.renewAfter.getTime() > System.currentTimeMillis()) {
                logger.tracef("Reusing previous session for login %s", login);
                return session;
            }

            sessions.remove(login);

            logger.tracef("Authenticating with GOSMS service under login %s", login);

            try {
                SimpleHttp.Response response = restService.login(login, pw, "client_credentials");
                if (response.getStatus() < 200 || response.getStatus() >= 300) {
                    throw new IllegalArgumentException("GOSMS server did not accept our credentials.");
                }

                GoSMSRestService.TokenResponse tokenResponse = response.asJson(GoSMSRestService.TokenResponse.class);

                session = new Session(login, tokenResponse.getAccessToken(), new Date(System.currentTimeMillis() + 1000L * tokenResponse.getExpiresIn() - 5000));
                sessions.put(login, session);

                logger.infof("GOSMS service authenticated under login %s and expiration %s", login, session.renewAfter.toString());

                return session;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class Session {
        public Session(String login, String accessToken, Date renewAfter) {
            this.login = login;
            this.accessToken = accessToken;
            this.renewAfter = renewAfter;
        }

        protected String login;
        protected String accessToken;
        protected Date renewAfter;
    }
}
