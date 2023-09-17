package eu.bindworks.keycloak.smsotp.credentials.phoneNumber;

import eu.bindworks.keycloak.smsotp.authenticator.CredentialsPhoneNumberUpdateActionFactory;
import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialTypeMetadata;
import org.keycloak.credential.CredentialTypeMetadataContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class SmsOtpPhoneNumberCredentialProvider implements CredentialProvider<SmsOtpPhoneNumberCredentialModel> {
    protected KeycloakSession session;

    public static SmsOtpPhoneNumberCredentialProvider forSession(KeycloakSession session) {
        return (SmsOtpPhoneNumberCredentialProvider) session.getProvider(CredentialProvider.class, SmsOtpPhoneNumberCredentialProviderFactory.PROVIDER_ID);
    }

    public SmsOtpPhoneNumberCredentialProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public String getType() {
        return SmsOtpPhoneNumberCredentialModel.TYPE;
    }

    @Override
    public SmsOtpPhoneNumberCredentialModel getCredentialFromModel(CredentialModel credentialModel) {
        return SmsOtpPhoneNumberCredentialModel.createFromCredentialModel(credentialModel);
    }

    @Override
    public CredentialModel createCredential(RealmModel realm, UserModel user, SmsOtpPhoneNumberCredentialModel credentialModel) {
        if (credentialModel.getCreatedDate() == null) {
            credentialModel.setCreatedDate(Time.currentTimeMillis());
        }

        user.credentialManager().getStoredCredentialsByTypeStream(getType())
                .forEach(it -> deleteCredential(realm, user, it.getId()));

        return user.credentialManager().createStoredCredential(credentialModel);
    }

    @Override
    public boolean deleteCredential(RealmModel realm, UserModel user, String credentialId) {
        return user.credentialManager().removeStoredCredentialById(credentialId);
    }

    @Override
    public CredentialTypeMetadata getCredentialTypeMetadata(CredentialTypeMetadataContext metadataContext) {
        return CredentialTypeMetadata.builder()
                .type(getType())
                .category(CredentialTypeMetadata.Category.TWO_FACTOR)
                .displayName("sms-otp-phone-number-display-name")
                .helpText("sms-otp-phone-number-help-text")
                .createAction(CredentialsPhoneNumberUpdateActionFactory.PROVIDER_ID)
                .removeable(true)
                .build(session);
    }
}
