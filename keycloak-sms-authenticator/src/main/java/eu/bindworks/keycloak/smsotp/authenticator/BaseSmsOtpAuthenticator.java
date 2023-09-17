package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.Utilities;
import eu.bindworks.keycloak.smsotp.credentials.otp.SmsOtpCredentialModel;
import eu.bindworks.keycloak.smsotp.credentials.otp.SmsOtpCredentialProvider;
import eu.bindworks.keycloak.smsotp.services.SmsService;
import eu.bindworks.keycloak.smsotp.services.SmsServiceFactory;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.Time;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Date;

abstract public class BaseSmsOtpAuthenticator implements Authenticator {
    protected final Logger logger = Logger.getLogger(this.getClass());

    protected abstract String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            UserModel user = context.getUser();

            String phoneNumber = determinePhoneNumber(session, realm, user);

            if (phoneNumber == null) {
                logger.errorf("Attempt to authenticate via SMS OTP with no phone number for user %s", user.getUsername());

                Response challenge = context.form()
                        .setAttribute("earliestResendAt", "0")
                        .setError("smsotp.error.noPhoneNumber")
                        .createForm("sms-otp-question.ftl");
                context.failureChallenge(AuthenticationFlowError.CREDENTIAL_SETUP_REQUIRED, challenge);
                return;
            }

            Date earliestResendAt = SmsOtpCredentialProvider.forSession(session).earliestResendAt(user);

            if (Time.toDate(Time.currentTimeMillis()).before(earliestResendAt)) {
                logger.tracef("Attempt to resend SMS OTP code too early for user %s", user.getUsername());

                Response challenge = context.form()
                        .setAttribute("earliestResendAt", String.valueOf(earliestResendAt.getTime()))
                        .setError("smsotp.error.resendNotAllowedYet")
                        .createForm("sms-otp-question.ftl");
                context.failureChallenge(AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR, challenge);
            } else {
                logger.tracef("Request to authenticate user %s via SMS OTP on number %s", user.getUsername(), phoneNumber);

                SmsOtpAuthenticatorConfig config = new SmsOtpAuthenticatorConfig(context.getAuthenticatorConfig());

                SmsOtpCredentialModel smsOtpCredential = createRandomSmsOtpCredential(session, realm, user, config);

                String autoFillSuffix = "";
                if (config.isAppendAutofill()) {
                    String browser = context.getHttpRequest().getHttpHeaders().getHeaderString(HttpHeaders.USER_AGENT);
                    if (browser != null && browser.toLowerCase().contains("android")) {
                        autoFillSuffix = "\n\n@" + context.getRefreshExecutionUrl().getHost() + " #" + smsOtpCredential.getSmsOtpSecretData().getValue();
                    }
                }

                String message = config.getMessage()
                        .replace("***", smsOtpCredential.getSmsOtpSecretData().getValue())
                        .replace("\\n", "\n")
                        + autoFillSuffix;

                SmsServiceFactory smsServiceFactory = config.getSmsService().getSmsServiceFactory();
                SmsService smsService = smsServiceFactory.create(session, config);

                smsService.sendSms(phoneNumber, message);

                logger.tracef("Success sending OTP SMS for user %s to number %s", user.getUsername(), phoneNumber);

                Response challenge = context.form()
                        .setAttribute("earliestResendAt", String.valueOf(smsOtpCredential.getSmsOtpCredentialData().getEarliestResendAt().getTime()))
                        .createForm("sms-otp-question.ftl");
                context.challenge(challenge);
            }
        } catch (Exception e) {
            logger.error("Error trying to send SMS message", e);

            Response challenge = context.form()
                    .setAttribute("earliestResendAt", "0")
                    .setError("smsotp.error.technicalError")
                    .createForm("sms-otp-question.ftl");
            context.failureChallenge(AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR, challenge);
        }
    }

    protected SmsOtpCredentialModel createRandomSmsOtpCredential(KeycloakSession session, RealmModel realm, UserModel user, SmsOtpAuthenticatorConfig config) {
        SmsOtpCredentialProvider smsOtpCredentialProvider = SmsOtpCredentialProvider.forSession(session);

        Date expiresAt = Time.toDate(Time.currentTimeMillis() + 1000L * config.getLifetime());
        Date earliestResendAt = Time.toDate(Time.currentTimeMillis() + 1000L * config.getResendDelay());
        int triesLeft = config.getMaxTries();
        String value = Utilities.createRandomOtp(config.getLength());

        SmsOtpCredentialModel otpCredentials = SmsOtpCredentialModel.create(expiresAt, earliestResendAt, triesLeft, value);
        smsOtpCredentialProvider.createCredential(realm, user, otpCredentials);

        return otpCredentials;
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();

        SmsOtpAuthenticatorForm form = new SmsOtpAuthenticatorForm(context);
        Date earliestResendAt = SmsOtpCredentialProvider.forSession(session).earliestResendAt(user);

        switch (form.getAction()) {
            case ANSWERED:
                if (SmsOtpCredentialProvider.forSession(session).isValid(realm, user, form.getAnswer())) {
                    logger.tracef("Success authenticating OTP SMS for user %s", user.getUsername());

                    context.success();
                } else {
                    logger.tracef("Wrong OTP SMS for user %s", user.getUsername());

                    Response challenge = context.form()
                            .setAttribute("earliestResendAt", String.valueOf(earliestResendAt.getTime()))
                            .setError("smsotp.error.wrongCode")
                            .createForm("sms-otp-question.ftl");
                    context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
                }
                break;

            case RESEND_REQUESTED:
                authenticate(context);
                break;

            case CANCELLED:
                SmsOtpCredentialProvider.forSession(session).deleteAllCredentials(realm, user);
                context.resetFlow();
                break;

            default:
                Response challenge = context.form()
                        .setAttribute("earliestResendAt", "0")
                        .setError("smsotp.error.technicalError")
                        .createForm("sms-otp-question.ftl");
                context.failureChallenge(AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR, challenge);
                break;
        }
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public void close() {
        // intentionally left blank
    }
}
