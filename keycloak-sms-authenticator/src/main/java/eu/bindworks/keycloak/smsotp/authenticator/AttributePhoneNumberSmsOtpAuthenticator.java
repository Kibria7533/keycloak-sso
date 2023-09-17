package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Collections;
import java.util.List;

public class AttributePhoneNumberSmsOtpAuthenticator extends BaseSmsOtpAuthenticator {
    private final String phoneNumberAttributeName;

    public AttributePhoneNumberSmsOtpAuthenticator(String phoneNumberAttributeName) {
        this.phoneNumberAttributeName = phoneNumberAttributeName;
    }

    @Override
    protected String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user) {
        return Utilities.determinePhoneNumberFromAttribute(session, realm, user, phoneNumberAttributeName);
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return determinePhoneNumber(session, realm, user) != null;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        user.addRequiredAction(AttributePhoneNumberUpdateActionFactory.PROVIDER_ID);
    }

    @Override
    public List<RequiredActionFactory> getRequiredActions(KeycloakSession session) {
        return Collections.singletonList(AttributePhoneNumberUpdateActionFactory.forSession(session));
    }
}
