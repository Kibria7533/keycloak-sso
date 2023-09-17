package eu.bindworks.keycloak.smsotp.credentials.phoneNumber;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsOtpPhoneNumberCredentialData {
    private final String phoneNumber;

    @JsonCreator
    public SmsOtpPhoneNumberCredentialData(
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
