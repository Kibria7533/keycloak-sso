package eu.bindworks.keycloak.smsotp.services.twilio;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;

import java.io.IOException;

public class TwilioService implements SmsService {
    private static final Logger logger = Logger.getLogger(TwilioService.class);
    private final TwilioRestService restService;
    private final String account;
    private final String authToken;
    private final String messagingServiceSid;

    public TwilioService(TwilioRestService restService, String account, String authToken, String messagingServiceSid) {
        this.restService = restService;
        this.account = account;
        this.authToken = authToken;
        this.messagingServiceSid = messagingServiceSid;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        try {
            SimpleHttp.Response response = this.restService.send(
                    account,
                    authToken,
                    messagingServiceSid,
                    phoneNumber,
                    message
            );

            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                String error = "Status: " + response.getStatus() + ", Response: " + response.asString();
                logger.error(error);
                throw new RuntimeException("Twilio server did not accept our SMS: " + error);
            }

            logger.tracef("SMS message sent to number %s", phoneNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
