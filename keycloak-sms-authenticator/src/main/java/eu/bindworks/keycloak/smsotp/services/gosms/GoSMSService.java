package eu.bindworks.keycloak.smsotp.services.gosms;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;

import java.io.IOException;

public class GoSMSService implements SmsService {
    private static final Logger logger = Logger.getLogger(GoSMSService.class);

    private final GoSMSRestService restService;

    private final String accessToken;
    private final String channel;

    public GoSMSService(GoSMSRestService restService, String accessToken, String channel) {
        this.restService = restService;
        this.accessToken = accessToken;
        this.channel = channel;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        logger.tracef("Sending sms message to number %s...", phoneNumber);

        try {
            SimpleHttp.Response response = restService.send(
                    accessToken,
                    channel,
                    phoneNumber,
                    message
            );

            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                String error = "Status: " + response.getStatus() + ", Response: " + response.asString();
                logger.error(error);
                throw new RuntimeException("GOSMS server did not accept our SMS: " + error);
            }

            logger.tracef("SMS message sent to number %s with response %d...", phoneNumber, response.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
