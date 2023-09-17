package eu.bindworks.keycloak.smsotp.services.sns;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class SNSService implements SmsService {
    private static final Logger logger = Logger.getLogger(SNSService.class);
    private final SnsClient snsClient;

    public SNSService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        PublishRequest request = PublishRequest.builder()
                .phoneNumber(phoneNumber)
                .message(message)
                .build();

        snsClient.publish(request);

        logger.tracef("SMS message sent to number %s", phoneNumber);
    }
}
