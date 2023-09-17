package eu.bindworks.keycloak.smsotp.credentials.otp;

import eu.bindworks.keycloak.smsotp.Utilities;
import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.Date;

public class SmsOtpCredentialModel extends CredentialModel {
    public static final String TYPE = "sms-otp";

    private final SmsOtpCredentialData credentialData;
    private final SmsOtpSecretData secretData;

    private SmsOtpCredentialModel(SmsOtpCredentialData credentialData, SmsOtpSecretData secretData) {
        this.credentialData = credentialData;
        this.secretData = secretData;
    }

    private SmsOtpCredentialModel(Date expiresAt, Date earliestResendAt, int triesLeft, String value) {
        this.credentialData = new SmsOtpCredentialData(expiresAt, earliestResendAt, triesLeft);
        this.secretData = new SmsOtpSecretData(value);
    }

    public static SmsOtpCredentialModel create(Date expiresAt, Date earliestResendAt, int triesLeft, String value) {
        SmsOtpCredentialModel credentialModel = new SmsOtpCredentialModel(expiresAt, earliestResendAt, triesLeft, value);
        credentialModel.fillCredentialModelFields();
        return credentialModel;
    }

    public SmsOtpCredentialModel withDecrementedTriesLeft() {
        Date expiresAt = credentialData.getExpiresAt();
        Date earliestResendAt = credentialData.getEarliestResendAt();
        int triesLeft = Math.max(0, credentialData.getTriesLeft() - 1);
        String value = secretData.getValue();

        SmsOtpCredentialModel model = SmsOtpCredentialModel.create(expiresAt, earliestResendAt, triesLeft, value);
        model.setId(this.getId());
        return model;
    }

    public static SmsOtpCredentialModel createFromCredentialModel(CredentialModel credentialModel) {
        try {
            SmsOtpCredentialData credentialData = JsonSerialization.readValue(credentialModel.getCredentialData(), SmsOtpCredentialData.class);
            SmsOtpSecretData secretData = JsonSerialization.readValue(credentialModel.getSecretData(), SmsOtpSecretData.class);

            SmsOtpCredentialModel model = new SmsOtpCredentialModel(credentialData, secretData);
            Utilities.copyCredentialModel(model, credentialModel);
            return model;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SmsOtpCredentialData getSmsOtpCredentialData() {
        return credentialData;
    }

    public SmsOtpSecretData getSmsOtpSecretData() {
        return secretData;
    }

    private void fillCredentialModelFields() {
        try {
            setSecretData(JsonSerialization.writeValueAsString(secretData));
            setCredentialData(JsonSerialization.writeValueAsString(credentialData));
            setType(TYPE);
            setCreatedDate(Time.currentTimeMillis());
            setUserLabel("SMS OTP");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
