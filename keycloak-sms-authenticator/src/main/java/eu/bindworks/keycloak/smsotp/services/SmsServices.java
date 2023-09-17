package eu.bindworks.keycloak.smsotp.services;

import eu.bindworks.keycloak.smsotp.services.gosms.GoSMSServiceFactory;
import eu.bindworks.keycloak.smsotp.services.infobip.InfobipServiceFactory;
import eu.bindworks.keycloak.smsotp.services.sns.SNSServiceFactory;
import eu.bindworks.keycloak.smsotp.services.twilio.TwilioServiceFactory;
import eu.bindworks.keycloak.smsotp.services.webhook.WebHookSMSServiceFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum SmsServices {
    GOSMS(new GoSMSServiceFactory()),
    INFOBIP(new InfobipServiceFactory()),
    SNS(new SNSServiceFactory()),
    TWILIO(new TwilioServiceFactory()),
    WEBHOOK(new WebHookSMSServiceFactory());

    private final SmsServiceFactory smsServiceFactory;

    SmsServices(SmsServiceFactory smsServiceFactory) {
        this.smsServiceFactory = smsServiceFactory;
    }

    public SmsServiceFactory getSmsServiceFactory() {
        return smsServiceFactory;
    }

    public static String[] allNames() {
        return Arrays.stream(SmsServices.values()).map(Enum::name).toArray(String[]::new);
    }

    public static String loginMeanings() {
        return compileMeanings(SmsServiceFactory.Metadata::getLoginMeaning);
    }

    public static String passwordMeanings() {
        return compileMeanings(SmsServiceFactory.Metadata::getPasswordMeaning);
    }

    public static String parameter1Meanings() {
        return compileMeanings(SmsServiceFactory.Metadata::getParameter1Meaning);
    }

    public static String parameter2Meanings() {
        return compileMeanings(SmsServiceFactory.Metadata::getParameter2Meaning);
    }

    @FunctionalInterface
    private interface MeaningExtractor {
        String getMeaning(SmsServiceFactory.Metadata metadata);
    }

    private static String compileMeanings(MeaningExtractor extractor) {
        return Arrays.stream(SmsServices.values())
                .map(it -> "" + it.name() + ":\u00a0" + extractor.getMeaning(it.getSmsServiceFactory().getMetadata()).replace(' ', '\u00a0'))
                .collect(Collectors.joining(", "));
    }
}
