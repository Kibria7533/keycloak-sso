package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class AttributePhoneNumberUpdateAction extends BasePhoneNumberUpdateAction {
    private final String phoneNumberAttributeName;

    public AttributePhoneNumberUpdateAction(KeycloakSession session, String phoneNumberAttributeName) {
        super(session);
        this.phoneNumberAttributeName = phoneNumberAttributeName;
    }

    @Override
    protected String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user) {
        return eu.bindworks.keycloak.smsotp.Utilities.determinePhoneNumberFromAttribute(session, realm, user, phoneNumberAttributeName);
    }

    @Override
    protected void updatePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user, String phoneNumber) {
        Utilities.updatePhoneNumberInAttribute(session, realm, user, phoneNumberAttributeName, phoneNumber);
    }
}
