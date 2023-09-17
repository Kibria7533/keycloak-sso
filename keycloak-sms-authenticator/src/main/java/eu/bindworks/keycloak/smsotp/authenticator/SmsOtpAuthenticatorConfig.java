package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.services.SmsServiceConfig;
import eu.bindworks.keycloak.smsotp.services.SmsServices;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.utils.StringUtil;

import java.util.List;

public class SmsOtpAuthenticatorConfig implements SmsServiceConfig {
    public static final String CONFIG_PROPERTY_SMSOTP_LENGTH = "length";
    public static final String CONFIG_PROPERTY_SMSOTP_LIFETIME = "lifetime";
    public static final String CONFIG_PROPERTY_SMSOTP_RESEND_DELAY = "resend-delay";
    public static final String CONFIG_PROPERTY_SMSOTP_MAXTRIES = "max-tries";
    public static final String CONFIG_PROPERTY_SMSOTP_MESSAGE = "message";
    public static final String CONFIG_PROPERTY_SMSOTP_APPEND_AUTOFILL = "append-autofill";
    public static final String CONFIG_PROPERTY_SMSOTP_SERVICE = "service";
    public static final String CONFIG_PROPERTY_SMSOTP_SERVICE_LOGIN = "serviceLogin";
    public static final String CONFIG_PROPERTY_SMSOTP_SERVICE_PASSWORD = "servicePassword";
    public static final String CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER1 = "serviceParameter1";
    public static final String CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER2 = "serviceParameter2";

    public static final int DEFAULT_VALUE_SMSOTP_LENGTH = 4;
    public static final int DEFAULT_VALUE_SMSOTP_LIFETIME = 60;
    public static final int DEFAULT_VALUE_SMSOTP_RESEND_DELAY = 20;
    public static final int DEFAULT_VALUE_SMSOTP_MAXTRIES = 3;
    public static final String DEFAULT_VALUE_SMSOTP_MESSAGE = "Your one time password is ***";
    public static final SmsServices DEFAULT_VALUE_SMSOTP_SERVICE = SmsServices.GOSMS;

    public static final List<ProviderConfigProperty> PROPERTIES = List.of(
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_LENGTH,
                    "Length of the OTP code",
                    "Number of digits in the OTP code.",
                    ProviderConfigProperty.STRING_TYPE,
                    String.valueOf(DEFAULT_VALUE_SMSOTP_LENGTH)
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_LIFETIME,
                    "Lifetime of OTP code",
                    "Lifetime of OTP code in seconds.",
                    ProviderConfigProperty.STRING_TYPE,
                    String.valueOf(DEFAULT_VALUE_SMSOTP_LIFETIME)
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_RESEND_DELAY,
                    "Delay before next OTP code can be requested",
                    "Minimum number of seconds to pass before another OTP can be requested.",
                    ProviderConfigProperty.STRING_TYPE,
                    String.valueOf(DEFAULT_VALUE_SMSOTP_RESEND_DELAY)
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_MAXTRIES,
                    "Maximum number of tries",
                    "Maximum number of tries before having to generate new code.",
                    ProviderConfigProperty.STRING_TYPE,
                    String.valueOf(DEFAULT_VALUE_SMSOTP_MAXTRIES)
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_MESSAGE,
                    "Message contents",
                    "Contents of the message sent via SMS. Three asterisks (***) are replaced with the actual OTP code. Backslash 'n' (\\n) is replaced with a newline",
                    ProviderConfigProperty.STRING_TYPE,
                    DEFAULT_VALUE_SMSOTP_MESSAGE
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_APPEND_AUTOFILL,
                    "Append autofill code for Android phones",
                    "Message will be extended with '@domain #code' when the request comes from Android phone, so that the browser is able to autofill the sms",
                    ProviderConfigProperty.BOOLEAN_TYPE,
                    "true"
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_SERVICE,
                    "SMS Service Provider",
                    "Provider of the SMS services.",
                    ProviderConfigProperty.LIST_TYPE,
                    DEFAULT_VALUE_SMSOTP_SERVICE.name(),
                    SmsServices.allNames()
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_SERVICE_LOGIN,
                    "SMS Service Login",
                    SmsServices.loginMeanings(),
                    ProviderConfigProperty.STRING_TYPE,
                    null
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_SERVICE_PASSWORD,
                    "SMS Service Authentication Secret",
                    SmsServices.passwordMeanings(),
                    ProviderConfigProperty.STRING_TYPE,
                    null,
                    true
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER1,
                    "SMS Service 1st Parameter",
                    SmsServices.parameter1Meanings(),
                    ProviderConfigProperty.STRING_TYPE,
                    null
            ),
            new ProviderConfigProperty(
                    CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER2,
                    "SMS Service 2nd Parameter",
                    SmsServices.parameter2Meanings(),
                    ProviderConfigProperty.STRING_TYPE,
                    null
            ));

    private final AuthenticatorConfigModel model;

    public SmsOtpAuthenticatorConfig(AuthenticatorConfigModel model) {
        this.model = model;
    }

    private int getConfigNumber(String propertyName, int defaultValue) {
        String value = model.getConfig().get(propertyName);
        if (StringUtil.isBlank(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getLength() {
        return getConfigNumber(CONFIG_PROPERTY_SMSOTP_LENGTH, DEFAULT_VALUE_SMSOTP_LENGTH);
    }

    public int getLifetime() {
        return getConfigNumber(CONFIG_PROPERTY_SMSOTP_LIFETIME, DEFAULT_VALUE_SMSOTP_LIFETIME);
    }

    public int getResendDelay() {
        return getConfigNumber(CONFIG_PROPERTY_SMSOTP_RESEND_DELAY, DEFAULT_VALUE_SMSOTP_RESEND_DELAY);
    }

    public int getMaxTries() {
        return getConfigNumber(CONFIG_PROPERTY_SMSOTP_MAXTRIES, DEFAULT_VALUE_SMSOTP_MAXTRIES);
    }

    public String getMessage() {
        String message = model.getConfig().get(CONFIG_PROPERTY_SMSOTP_MESSAGE);
        if (StringUtil.isBlank(message)) {
            return DEFAULT_VALUE_SMSOTP_MESSAGE;
        }
        if (!message.contains("***")) {
            message = message + " ***";
        }
        return message;
    }

    public boolean isAppendAutofill() {
        String value = model.getConfig().get(CONFIG_PROPERTY_SMSOTP_APPEND_AUTOFILL);
        return "true".equalsIgnoreCase(value);
    }

    public SmsServices getSmsService() {
        String smsService = model.getConfig().get(CONFIG_PROPERTY_SMSOTP_SERVICE);
        try {
            return SmsServices.valueOf(smsService);
        } catch (Exception e) {
            return DEFAULT_VALUE_SMSOTP_SERVICE;
        }
    }

    public String getSmsServiceLogin() {
        return model.getConfig().get(CONFIG_PROPERTY_SMSOTP_SERVICE_LOGIN);
    }

    public String getSmsServicePassword() {
        return model.getConfig().get(CONFIG_PROPERTY_SMSOTP_SERVICE_PASSWORD);
    }

    public String getSmsServiceParameter1() {
        return model.getConfig().get(CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER1);
    }

    public String getSmsServiceParameter2() {
        return model.getConfig().get(CONFIG_PROPERTY_SMSOTP_SERVICE_PARAMETER2);
    }
}
