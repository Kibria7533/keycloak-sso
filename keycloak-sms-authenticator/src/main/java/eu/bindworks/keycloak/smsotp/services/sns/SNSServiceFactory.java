package eu.bindworks.keycloak.smsotp.services.sns;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.utils.StringUtil;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

public class SNSServiceFactory implements SmsServiceFactory {
    private static final Metadata metadata = new Metadata("access key id", "secret key", "region", "-");

    @Override
    public SmsService create(KeycloakSession session, SmsServiceConfig config) {
        Region region = determineRegion(config.getSmsServiceParameter1());

        AwsCredentials credentials = AwsBasicCredentials.create(
                config.getSmsServiceLogin(),
                config.getSmsServicePassword());

        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        SnsClient client = SnsClient.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        return new SNSService(client);
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    private Region determineRegion(String parameter1) {
        if (StringUtil.isBlank(parameter1)) {
            return Region.US_EAST_1;
        }
        return Region.of(parameter1);
    }
}
