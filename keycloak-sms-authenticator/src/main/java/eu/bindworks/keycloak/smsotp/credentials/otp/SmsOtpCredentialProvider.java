package eu.bindworks.keycloak.smsotp.credentials.otp;

import org.jboss.logging.Logger;
import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialTypeMetadata;
import org.keycloak.credential.CredentialTypeMetadataContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;

import java.util.Date;
import java.util.stream.Stream;

public class SmsOtpCredentialProvider implements CredentialProvider<SmsOtpCredentialModel>, CredentialInputValidator {
    private static final Logger logger = Logger.getLogger(SmsOtpCredentialProvider.class);

    protected KeycloakSession session;

    public static SmsOtpCredentialProvider forSession(KeycloakSession session) {
        return (SmsOtpCredentialProvider) session.getProvider(CredentialProvider.class, SmsOtpCredentialProviderFactory.PROVIDER_ID);
    }

    public SmsOtpCredentialProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public String getType() {
        return SmsOtpCredentialModel.TYPE;
    }

    @Override
    public SmsOtpCredentialModel getCredentialFromModel(CredentialModel model) {
        return SmsOtpCredentialModel.createFromCredentialModel(model);
    }

    @Override
    public CredentialModel createCredential(RealmModel realm, UserModel user, SmsOtpCredentialModel credentialModel) {
        if (credentialModel.getCreatedDate() == null) {
            credentialModel.setCreatedDate(Time.currentTimeMillis());
        }
        return user.credentialManager().createStoredCredential(credentialModel);
    }

    @Override
    public boolean deleteCredential(RealmModel realm, UserModel user, String credentialId) {
        return user.credentialManager().removeStoredCredentialById(credentialId);
    }

    public void updateCredential(UserModel user, SmsOtpCredentialModel credentialModel) {
        user.credentialManager().updateStoredCredential(credentialModel);
    }

    private Stream<SmsOtpCredentialModel> getStoredCredentialsStream(UserModel user) {
        return user.credentialManager().getStoredCredentialsByTypeStream(SmsOtpCredentialModel.TYPE)
                .map(this::getCredentialFromModel);
    }

    public Stream<SmsOtpCredentialModel> getNonExpiredCredentials(UserModel user) {
        Date cutoff = Time.toDate(Time.currentTimeMillis());
        return getStoredCredentialsStream(user)
                .filter(it -> !it.getSmsOtpCredentialData().isExpired(cutoff));
    }

    public void decreaseTriesOfCredentials(UserModel user) {
        getNonExpiredCredentials(user)
                .forEach(it -> updateCredential(user, it.withDecrementedTriesLeft()));
    }

    public void deleteAllCredentials(RealmModel realm, UserModel user) {
        getStoredCredentialsStream(user)
                .forEach(it -> deleteCredential(realm, user, it.getId()));
    }

    public Date earliestResendAt(UserModel user) {
        return getNonExpiredCredentials(user)
                .map(it -> it.getSmsOtpCredentialData().getEarliestResendAt())
                .max(Date::compareTo)
                .orElse(new Date(0));
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        if (!(credentialInput instanceof UserCredentialModel)) {
            logger.debugf("Expected instance of UserCredentialModel for CredentialInput, got %s", credentialInput.getType());
            return false;
        }

        if (!credentialInput.getType().equals(getType())) {
            return false;
        }

        String challengeResponse = credentialInput.getChallengeResponse();
        if (challengeResponse == null) {
            return false;
        }

        SmsOtpCredentialModel credentialModel = getNonExpiredCredentials(user)
                .filter(it -> it.getSmsOtpSecretData().getValue().equals(challengeResponse))
                .findFirst()
                .orElse(null);

        if (credentialModel == null) {
            decreaseTriesOfCredentials(user);
            return false;
        }

        this.deleteAllCredentials(realm, user);
        return true;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return getType().equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if (!supportsCredentialType(credentialType)) {
            return false;
        }
        return getStoredCredentialsStream(user).findAny().isPresent();
    }

    @Override
    public CredentialTypeMetadata getCredentialTypeMetadata(CredentialTypeMetadataContext metadataContext) {
        return CredentialTypeMetadata.builder()
                .type(getType())
                .category(CredentialTypeMetadata.Category.TWO_FACTOR)
                .displayName(SmsOtpCredentialProviderFactory.PROVIDER_ID)
                .helpText("sms-otp-text")
                .removeable(false)
                .build(session);
    }
}
