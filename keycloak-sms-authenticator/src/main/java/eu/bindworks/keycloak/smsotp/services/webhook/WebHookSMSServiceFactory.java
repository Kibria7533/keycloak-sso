package eu.bindworks.keycloak.smsotp.services.webhook;

import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.keycloak.models.KeycloakSession;

public class WebHookSMSServiceFactory implements SmsServiceFactory {
    private static final Metadata metadata = new Metadata("login", "password", "url", "body template");

    @Override
    public SmsService create(KeycloakSession keycloakSession, SmsServiceConfig config) {
        return new WebHookSMSService(keycloakSession, config.getSmsServiceLogin(), config.getSmsServicePassword(), config.getSmsServiceParameter1(), config.getSmsServiceParameter2());
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }
}
