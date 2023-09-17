package eu.bindworks.keycloak.smsotp.services.infobip;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.utils.StringUtil;

public class InfobipServiceFactory implements SmsServiceFactory {
    private static final Metadata metadata = new Metadata("baseURL", "API Key", "from", "-");

    @Override
    public SmsService create(KeycloakSession session, SmsServiceConfig config) {
        InfobipRestService restService = new InfobipRestService(session);
        String from = StringUtil.isBlank(config.getSmsServiceParameter1()) ? null : config.getSmsServiceParameter1();

        return new InfobipService(
                restService,
                config.getSmsServiceLogin(),
                config.getSmsServicePassword(),
                from
        );
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }
}
