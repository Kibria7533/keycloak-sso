package eu.bindworks.keycloak.smsotp.credentials.otp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SmsOtpCredentialData {
    private final Date expiresAt;
    private final Date earliestResendAt;
    private final int triesLeft;

    @JsonCreator
    public SmsOtpCredentialData(
            @JsonProperty("expiresAt") Date expiresAt,
            @JsonProperty("earliestResendAt") Date earliestResendAt,
            @JsonProperty("triesLeft") int triesLeft
    ) {
        this.expiresAt = expiresAt;
        this.earliestResendAt = earliestResendAt;
        this.triesLeft = triesLeft;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public Date getEarliestResendAt() {
        return earliestResendAt;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public boolean isExpired(Date cutoff) {
        return triesLeft == 0 || expiresAt.before(cutoff);
    }
}
