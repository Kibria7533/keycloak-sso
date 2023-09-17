package eu.bindworks.keycloak.smsotp.authenticator;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.Response;
import java.util.regex.Pattern;

abstract public class BasePhoneNumberUpdateAction implements RequiredActionProvider {
    protected static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+\\d{5,}$");
    protected final KeycloakSession session;

    public BasePhoneNumberUpdateAction(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void close() {
        // intentionally left blank
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // intentionally left blank
    }

    protected abstract String determinePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user);

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        String phoneNumber = determinePhoneNumber(context.getSession(), context.getRealm(), context.getUser());

        Response challenge = context.form()
                .setAttribute("phoneNumber", phoneNumber)
                .createForm("sms-otp-config.ftl");
        context.challenge(challenge);
    }

    protected abstract void updatePhoneNumber(KeycloakSession session, RealmModel realm, UserModel user, String phoneNumber);

    @Override
    public void processAction(RequiredActionContext context) {
        String phoneNumber = (context.getHttpRequest().getDecodedFormParameters().getFirst("phoneNumber"));

        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            Response challenge = context.form()
                    .setAttribute("phoneNumber", phoneNumber)
                    .setError("smsotp.error.phonePattern")
                    .createForm("sms-otp-config.ftl");
            context.challenge(challenge);
            return;
        }

        updatePhoneNumber(context.getSession(), context.getRealm(), context.getUser(), phoneNumber);
        context.success();
    }
}
