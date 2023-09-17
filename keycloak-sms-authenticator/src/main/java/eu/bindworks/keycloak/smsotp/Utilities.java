package eu.bindworks.keycloak.smsotp;

import eu.bindworks.keycloak.smsotp.credentials.phoneNumber.SmsOtpPhoneNumberCredentialModel;
import eu.bindworks.keycloak.smsotp.credentials.phoneNumber.SmsOtpPhoneNumberCredentialProvider;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.security.SecureRandom;
import java.util.List;

public class Utilities {
    public static final String DEFAULT_PHONE_NUMBER_USER_ATTRIBUTE_NAME = "authenticationPhoneNumber";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String createRandomOtp(int length) {
        int value = secureRandom.nextInt((int) Math.round(Math.pow(10, length)));
        StringBuilder s = new StringBuilder(length);
        s.append(value);
        while (s.length() < length) {
            s.insert(0, '0');
        }
        return s.toString();
    }

    public static void copyCredentialModel(CredentialModel targetModel, CredentialModel sourceModel) {
        targetModel.setCreatedDate(sourceModel.getCreatedDate());
        targetModel.setCredentialData(sourceModel.getCredentialData());
        targetModel.setSecretData(sourceModel.getSecretData());
        targetModel.setId(sourceModel.getId());
        targetModel.setSecretData(sourceModel.getSecretData());
        targetModel.setType(sourceModel.getType());
        targetModel.setUserLabel(sourceModel.getUserLabel());
    }

    public static String determinePhoneNumberFromAttribute(KeycloakSession session, RealmModel realm, UserModel user, String attributeName) {
        List<String> phoneNumbers = user.getAttributes().get(attributeName);
        if (phoneNumbers == null || phoneNumbers.size() != 1) {
            return null;
        }

        return phoneNumbers.get(0);
    }

    public static void updatePhoneNumberInAttribute(KeycloakSession session, RealmModel realm, UserModel user, String attributeName, String phoneNumber) {
        user.setAttribute(attributeName, List.of(phoneNumber));
    }

    public static String determinePhoneNumberFromCredentials(KeycloakSession session, RealmModel realm, UserModel user) {
        SmsOtpPhoneNumberCredentialProvider smsOtpPhoneNumberCredentialProvider = SmsOtpPhoneNumberCredentialProvider.forSession(session);
        SmsOtpPhoneNumberCredentialModel phoneCredentials = smsOtpPhoneNumberCredentialProvider.getDefaultCredential(session, realm, user);

        if (phoneCredentials == null) {
            return null;
        }

        return phoneCredentials.getSmsOtpPhoneNumberCredentialData().getPhoneNumber();
    }

    public static void updatePhoneNumberInCredentials(KeycloakSession session, RealmModel realm, UserModel user, String phoneNumber) {
        SmsOtpPhoneNumberCredentialProvider smsOtpPhoneNumberCredentialProvider = SmsOtpPhoneNumberCredentialProvider.forSession(session);
        smsOtpPhoneNumberCredentialProvider.createCredential(realm, user, SmsOtpPhoneNumberCredentialModel.create(phoneNumber));
    }
}
