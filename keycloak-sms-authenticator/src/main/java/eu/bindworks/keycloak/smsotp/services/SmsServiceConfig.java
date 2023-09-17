package eu.bindworks.keycloak.smsotp.services;

public interface SmsServiceConfig {
    String getSmsServiceLogin();

    String getSmsServicePassword();

    String getSmsServiceParameter1();

    String getSmsServiceParameter2();
}
