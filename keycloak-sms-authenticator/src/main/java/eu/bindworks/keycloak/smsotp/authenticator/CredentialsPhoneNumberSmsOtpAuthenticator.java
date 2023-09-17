package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import eu.bindworks.keycloak.smsotp.credentials.phoneNumber.SmsOtpPhoneNumberCredentialModel;
import eu.bindworks.keycloak.smsotp.credentials.phoneNumber.SmsOtpPhoneNumberCredentialProvider;
import org.keycloak.authentication.CredentialValidator;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Collections;
import java.util.List;

public class CredentialsPhoneNumberSmsOtpAuthenticator extends BaseSmsOtpAuthenticator implements CredentialValidator<SmsOtpPhoneNumberCredentialProvider> {
    @Override
    protected String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user) {
        return Utilities.determinePhoneNumberFromCredentials(session, realm, user);
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return SmsOtpPhoneNumberCredentialModel.isConfiguredFor(user);
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        user.addRequiredAction(CredentialsPhoneNumberUpdateActionFactory.PROVIDER_ID);
    }

    @Override
    public List<RequiredActionFactory> getRequiredActions(KeycloakSession session) {
        return Collections.singletonList(CredentialsPhoneNumberUpdateActionFactory.forSession(session));
    }

    @Override
    public SmsOtpPhoneNumberCredentialProvider getCredentialProvider(KeycloakSession session) {
        return SmsOtpPhoneNumberCredentialProvider.forSession(session);
    }
}
