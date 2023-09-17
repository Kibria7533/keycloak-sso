package eu.bindworks.keycloak.smsotp.services;

import org.keycloak.models.KeycloakSession;

public interface SmsServiceFactory {
    SmsService create(KeycloakSession session, SmsServiceConfig config);

    Metadata getMetadata();

    class Metadata {
        private final String loginMeaning;
        private final String passwordMeaning;
        private final String parameter1Meaning;
        private final String parameter2Meaning;

        public Metadata(String loginMeaning, String passwordMeaning, String parameter1Meaning, String parameter2Meaning) {
            this.loginMeaning = loginMeaning;
            this.passwordMeaning = passwordMeaning;
            this.parameter1Meaning = parameter1Meaning;
            this.parameter2Meaning = parameter2Meaning;
        }

        public String getLoginMeaning() {
            return loginMeaning;
        }

        public String getPasswordMeaning() {
            return passwordMeaning;
        }

        public String getParameter1Meaning() {
            return parameter1Meaning;
        }

        public String getParameter2Meaning() {
            return parameter2Meaning;
        }
    }
}
