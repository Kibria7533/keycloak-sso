package eu.bindworks.keycloak.smsotp.services.infobip;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;

import java.io.IOException;

public class InfobipService implements SmsService {
    private static final Logger logger = Logger.getLogger(InfobipService.class);

    private final InfobipRestService restService;
    private final String baseUrl;
    private final String apiKey;
    private final String from;

    public InfobipService(InfobipRestService restService, String baseUrl, String apiKey, String from) {
        this.restService = restService;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.from = from;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        try {
            SimpleHttp.Response response = this.restService.send(
                    baseUrl,
                    apiKey,
                    from,
                    phoneNumber,
                    message
            );

            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                String error = "Status: " + response.getStatus() + ", Response: " + response.asString();
                logger.error(error);
                throw new RuntimeException("Infobip server did not accept our SMS: " + error);
            }

            logger.tracef("SMS message sent to number %s with response %d", phoneNumber, response.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
