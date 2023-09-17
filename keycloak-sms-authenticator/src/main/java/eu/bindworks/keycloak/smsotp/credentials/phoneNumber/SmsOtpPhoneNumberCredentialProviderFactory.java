package eu.bindworks.keycloak.smsotp.credentials.phoneNumber;

import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

public class SmsOtpPhoneNumberCredentialProviderFactory implements CredentialProviderFactory<SmsOtpPhoneNumberCredentialProvider> {
    public static final String PROVIDER_ID = "sms-otp-phone-number";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public SmsOtpPhoneNumberCredentialProvider create(KeycloakSession session) {
        return new SmsOtpPhoneNumberCredentialProvider(session);
    }
}
