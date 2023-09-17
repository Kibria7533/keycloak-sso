package eu.bindworks.keycloak.smsotp.services.gosms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;

public class GoSMSRestService {
    private final KeycloakSession session;

    public GoSMSRestService(KeycloakSession session) {
        this.session = session;
    }

    public SimpleHttp.Response login(
            String clientId,
            String clientSecret,
            String grantType
    ) throws IOException {
        return SimpleHttp
                .doPost("https://app.gosms.cz/oauth/v2/token", session)
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .param("grant_type", grantType)
                .acceptJson()
                .asResponse();
    }

    public SimpleHttp.Response send(
            String authorization,
            String channel,
            String to,
            String text
    ) throws IOException {
        return SimpleHttp
                .doPost("https://app.gosms.cz/api/v1/messages/", session)
                .auth(authorization)
                .json(new Sms(to, text, channel))
                .acceptJson()
                .asResponse();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenResponse {

        private final String accessToken;
        private final int expiresIn;

        @JsonCreator
        public TokenResponse(
                @JsonProperty("access_token") String accessToken,
                @JsonProperty("expires_in") int expiresIn
        ) {
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
        }

        @JsonProperty("access_token")
        public String getAccessToken() {
            return accessToken;
        }

        @JsonProperty("expires_in")
        public int getExpiresIn() {
            return expiresIn;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Sms {
        private final String recipients;
        private final String message;
        private final String channel;

        public Sms(String recipients, String message, String channel) {
            this.recipients = recipients;
            this.message = message;
            this.channel = channel;
        }

        @JsonProperty
        public String getRecipients() {
            return recipients;
        }

        @JsonProperty
        public String getMessage() {
            return message;
        }

        @JsonProperty
        public String getChannel() {
            return channel;
        }
    }
}
