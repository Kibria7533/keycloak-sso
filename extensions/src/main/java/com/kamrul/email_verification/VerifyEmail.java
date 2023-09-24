package com.kamrul.email_verification;

import com.kamrul.email_verification.utils.SmsConstants;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.models.*;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.theme.Theme;

import java.util.Locale;

public class VerifyEmail implements Authenticator {
    private static final String MOBILE_NUMBER_FIELD = "mobile_number";
    private static final String FORM_OTP_VERIFICATION = "login-sms.ftl";
    private static final Logger log = Logger.getLogger(VerifyEmail.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        KeycloakSession session = context.getSession();
        UserModel user = context.getUser();

        log.info("--------------------------------------");
        log.info(user);

        String mobileNumber = user.getFirstAttribute(MOBILE_NUMBER_FIELD);

        // Get the code length and code ttl from keycloak flow configuration(We configured it in factory)
        int length = Integer.parseInt(config.getConfig().get(SmsConstants.CODE_LENGTH));
        int ttl = Integer.parseInt(config.getConfig().get(SmsConstants.CODE_TTL));

        // Generate random code using keycloak and set that code and ttl in the AuthNote to get it in the next page.
        // (Like we set values in the session)
        String code = SecretGenerator.getInstance().randomString(length, SecretGenerator.DIGITS);
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        authSession.setAuthNote(SmsConstants.CODE, code);
        authSession.setAuthNote(SmsConstants.CODE_TTL, Long.toString(System.currentTimeMillis() + (ttl * 1000L)));

        try {
            Theme theme = session.theme().getTheme(Theme.Type.LOGIN);
            Locale locale = session.getContext().resolveLocale(user);
            String smsAuthText = theme.getMessages(locale).getProperty("smsAuthText");
            String smsText = String.format(smsAuthText, code, Math.floorDiv(ttl, 60));

            log.info(String.format("----------------------Your Auth Code Is : %s and validate for %s", code, Math.floorDiv(ttl, 60)));

//            SmsServiceFactory.get(config.getConfig()).send(mobileNumber, smsText);

            context.challenge(context.form().setAttribute("realm", context.getRealm()).createForm(FORM_OTP_VERIFICATION));
        } catch (Exception e) {
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("smsAuthSmsNotSent", e.getMessage())
                            .createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public void action(AuthenticationFlowContext context) {
        String enteredCode = context.getHttpRequest().getDecodedFormParameters().getFirst(SmsConstants.CODE);

        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        String code = authSession.getAuthNote(SmsConstants.CODE);
        String ttl = authSession.getAuthNote(SmsConstants.CODE_TTL);

        if (code == null || ttl == null) {
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
            return;
        }

        boolean isValid = enteredCode.equals(code);
        if (isValid) {
            if (Long.parseLong(ttl) < System.currentTimeMillis()) {
                // expired
                context.failureChallenge(AuthenticationFlowError.EXPIRED_CODE,
                        context.form().setError("smsAuthCodeExpired").createErrorPage(Response.Status.BAD_REQUEST));
            } else {
                // valid
                context.success();
            }
        } else {
            // invalid
            AuthenticationExecutionModel execution = context.getExecution();
            if (execution.isRequired()) {
                context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                        context.form().setAttribute("realm", context.getRealm())
                                .setError("smsAuthCodeInvalid").createForm(FORM_OTP_VERIFICATION));
            } else if (execution.isConditional() || execution.isAlternative()) {
                context.attempted();
            }
        }
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
       return userModel.getFirstAttribute(MOBILE_NUMBER_FIELD) != null;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        userModel.addRequiredAction("mobile-number-ra");
    }

    @Override
    public void close() {

    }
}
