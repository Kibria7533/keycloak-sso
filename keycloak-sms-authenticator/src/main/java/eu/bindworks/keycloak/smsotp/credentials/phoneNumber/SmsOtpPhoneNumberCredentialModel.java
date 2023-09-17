package eu.bindworks.keycloak.smsotp.credentials.phoneNumber;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;

public class SmsOtpPhoneNumberCredentialModel extends CredentialModel {
    public static final String TYPE = "sms-otp-phone-number";

    private final SmsOtpPhoneNumberCredentialData credentialData;

    private SmsOtpPhoneNumberCredentialModel(SmsOtpPhoneNumberCredentialData credentialData) {
        this.credentialData = credentialData;
    }

    private SmsOtpPhoneNumberCredentialModel(String phoneNumber) {
        this.credentialData = new SmsOtpPhoneNumberCredentialData(phoneNumber);
    }

    public static SmsOtpPhoneNumberCredentialModel create(String phoneNumber) {
        SmsOtpPhoneNumberCredentialModel credentialModel = new SmsOtpPhoneNumberCredentialModel(phoneNumber);
        credentialModel.fillCredentialModelFields();
        return credentialModel;
    }

    public static SmsOtpPhoneNumberCredentialModel createFromCredentialModel(CredentialModel credentialModel) {
        try {
            SmsOtpPhoneNumberCredentialData credentialData = JsonSerialization.readValue(credentialModel.getCredentialData(), SmsOtpPhoneNumberCredentialData.class);

            SmsOtpPhoneNumberCredentialModel model = new SmsOtpPhoneNumberCredentialModel(credentialData);
            Utilities.copyCredentialModel(model, credentialModel);
            return model;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isConfiguredFor(UserModel user) {
        return user.credentialManager().getStoredCredentialsByTypeStream(TYPE).findAny().isPresent();
    }

    public SmsOtpPhoneNumberCredentialData getSmsOtpPhoneNumberCredentialData() {
        return credentialData;
    }

    private void fillCredentialModelFields() {
        try {
            setCredentialData(JsonSerialization.writeValueAsString(credentialData));
            setType(TYPE);
            setCreatedDate(Time.currentTimeMillis());
            setUserLabel(credentialData.getPhoneNumber());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
