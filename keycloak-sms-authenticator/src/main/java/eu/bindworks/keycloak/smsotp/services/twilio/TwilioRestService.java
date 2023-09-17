package eu.bindworks.keycloak.smsotp.services.twilio;

import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.common.util.Encode;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;

public class TwilioRestService {
    private final KeycloakSession session;

    public TwilioRestService(KeycloakSession session) {
        this.session = session;
    }

    public SimpleHttp.Response send(
            String account,
            String authToken,
            String messagingServiceSid,
            String to,
            String body
    ) throws IOException {
        return SimpleHttp
                .doPost("https://api.twilio.com/2010-04-01/Accounts/" + Encode.encodeFragment(account) + "/Messages.json", session)
                .authBasic(account, authToken)
                .param("To", to)
                .param("MessagingServiceSid", messagingServiceSid)
                .param("Body", body)
                .acceptJson()
                .asResponse();
    }
}
