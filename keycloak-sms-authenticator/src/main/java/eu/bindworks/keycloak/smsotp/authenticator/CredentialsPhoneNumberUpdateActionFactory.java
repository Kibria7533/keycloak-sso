package eu.bindworks.keycloak.smsotp.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CredentialsPhoneNumberUpdateActionFactory implements RequiredActionFactory {
    public static final String PROVIDER_ID = "sms-otp-phone-number-config";

    public static CredentialsPhoneNumberUpdateActionFactory forSession(KeycloakSession session) {
        return (CredentialsPhoneNumberUpdateActionFactory) session.getKeycloakSessionFactory().getProviderFactory(RequiredActionProvider.class, PROVIDER_ID);
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new CredentialsPhoneNumberUpdateAction(session);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "SMS OTP Phone Number (credential)";
    }

    @Override
    public boolean isOneTimeAction() {
        return true;
    }

    @Override
    public void init(Config.Scope config) {
        // intentionally left blank
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
