package eu.bindworks.keycloak.smsotp.services.infobip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.common.util.Encode;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InfobipRestService {
    private final KeycloakSession session;

    public InfobipRestService(KeycloakSession session) {
        this.session = session;
    }

    public SimpleHttp.Response send(
            String baseUrl,
            String apiKey,
            String from,
            String to,
            String body
    ) throws IOException {
        return SimpleHttp
                .doPost("https://" + Encode.encodeFragment(baseUrl) + "/sms/2/text/advanced", session)
                .header("Authorization", "App " + Encode.encodeFragment(apiKey))
                .json(new SmsMessages(new Sms(to, from, body)))
                .acceptJson()
                .asResponse();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SmsMessages {
        private final List<Sms> messages;

        public SmsMessages(Sms message) {
            this.messages = Collections.singletonList(message);
        }

        @JsonProperty
        public List<Sms> getMessages() {
            return messages;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Sms {
        private final List<SmsDestination> destinations;
        private final String from;
        private final String text;

        public Sms(String destination, String from, String text) {
            this.destinations = Collections.singletonList(new SmsDestination(destination));
            this.from = from;
            this.text = text;
        }

        @JsonProperty
        public List<SmsDestination> getDestinations() {
            return destinations;
        }

        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getFrom() {
            return from;
        }

        @JsonProperty
        public String getText() {
            return text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SmsDestination {
        private final String to;

        public SmsDestination(String to) {
            this.to = to;
        }

        @JsonProperty
        public String getTo() {
            return to;
        }
    }
}
