package eu.bindworks.keycloak.smsotp.services.twilio;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.keycloak.models.KeycloakSession;

public class TwilioServiceFactory implements SmsServiceFactory {
    private static final Metadata metadata = new Metadata("account", "auth token", "messaging service SID", "-");

    @Override
    public SmsService create(KeycloakSession session, SmsServiceConfig config) {
        TwilioRestService restService = new TwilioRestService(session);

        return new TwilioService(
                restService,
                config.getSmsServiceLogin(),
                config.getSmsServicePassword(),
                config.getSmsServiceParameter1());
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }
}
