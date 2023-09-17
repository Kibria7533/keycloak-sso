package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.credentials.phoneNumber.SmsOtpPhoneNumberCredentialModel;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class CredentialsPhoneNumberSmsOtpAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "sms-otp-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new CredentialsPhoneNumberSmsOtpAuthenticator();
    }

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return SmsOtpAuthenticatorConfig.PROPERTIES;
    }

    @Override
    public String getHelpText() {
        return "Validate possession of a phone via SMS OTP (user credential)";
    }

    @Override
    public String getDisplayType() {
        return "SMS OTP (user credential)";
    }

    @Override
    public String getReferenceCategory() {
        return SmsOtpPhoneNumberCredentialModel.TYPE;
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
