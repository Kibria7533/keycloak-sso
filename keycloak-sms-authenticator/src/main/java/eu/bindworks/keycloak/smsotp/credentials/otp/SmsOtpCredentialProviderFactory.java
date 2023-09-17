package eu.bindworks.keycloak.smsotp.credentials.otp;

import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

public class SmsOtpCredentialProviderFactory implements CredentialProviderFactory<SmsOtpCredentialProvider> {
    public static final String PROVIDER_ID = "sms-otp";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public SmsOtpCredentialProvider create(KeycloakSession session) {
        return new SmsOtpCredentialProvider(session);
    }
}
