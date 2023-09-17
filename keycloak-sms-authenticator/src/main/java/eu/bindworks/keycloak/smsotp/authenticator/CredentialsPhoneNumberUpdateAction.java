package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.authentication.CredentialRegistrator;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class CredentialsPhoneNumberUpdateAction extends BasePhoneNumberUpdateAction implements CredentialRegistrator {
    public CredentialsPhoneNumberUpdateAction(KeycloakSession session) {
        super(session);
    }

    @Override
    protected String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user) {
        return eu.bindworks.keycloak.smsotp.Utilities.determinePhoneNumberFromCredentials(session, realm, user);
    }

    @Override
    protected void updatePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user, String phoneNumber) {
        Utilities.updatePhoneNumberInCredentials(session, realm, user, phoneNumber);
    }

    @Override
    public InitiatedActionSupport initiatedActionSupport() {
        return InitiatedActionSupport.SUPPORTED;
    }

}
