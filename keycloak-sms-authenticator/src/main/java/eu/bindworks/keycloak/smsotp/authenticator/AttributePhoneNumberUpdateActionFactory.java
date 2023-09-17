package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class AttributePhoneNumberUpdateActionFactory implements RequiredActionFactory {
    public static final String PROVIDER_ID = "sms-otp-attribute-phone-number-config";

    private String phoneNumberAttributeName = Utilities.DEFAULT_PHONE_NUMBER_USER_ATTRIBUTE_NAME;

    public static AttributePhoneNumberUpdateActionFactory forSession(KeycloakSession session) {
        return (AttributePhoneNumberUpdateActionFactory) session.getKeycloakSessionFactory().getProviderFactory(RequiredActionProvider.class, PROVIDER_ID);
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new AttributePhoneNumberUpdateAction(session, phoneNumberAttributeName);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "SMS OTP Phone Number (attribute)";
    }

    @Override
    public boolean isOneTimeAction() {
        return true;
    }

    @Override
    public void init(Config.Scope config) {
        phoneNumberAttributeName = config.get("attribute", Utilities.DEFAULT_PHONE_NUMBER_USER_ATTRIBUTE_NAME);
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // intentionally left blank
    }

    @Override
    public void close() {
        // intentionally left blank
    }
}
