package eu.bindworks.keycloak.smsotp.services;

public interface SmsService {
    void sendSms(String phoneNumber, String message);
}
