package eu.bindworks.keycloak.smsotp.credentials.otp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsOtpSecretData {
    private final String value;

    @JsonCreator
    public SmsOtpSecretData(
            @JsonProperty("value") String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
