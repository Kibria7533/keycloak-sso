package eu.bindworks.keycloak.smsotp.authenticator;

import eu.bindworks.keycloak.smsotp.credentials.otp.SmsOtpCredentialModel;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.UserCredentialModel;

import javax.ws.rs.core.MultivaluedMap;

public class SmsOtpAuthenticatorForm {
    private final AuthenticationFlowContext authenticationFlowContext;
    private Action action;
    private UserCredentialModel answer;

    public enum Action {
        ANSWERED,
        RESEND_REQUESTED,
        CANCELLED,
    }

    public SmsOtpAuthenticatorForm(AuthenticationFlowContext authenticationFlowContext) {
        this.authenticationFlowContext = authenticationFlowContext;
        this.parseForm();
    }

    public Action getAction() {
        return action;
    }

    public UserCredentialModel getAnswer() {
        return answer;
    }

    private void parseForm() {
        MultivaluedMap<String, String> formData = authenticationFlowContext.getHttpRequest().getDecodedFormParameters();
        if (formData.containsKey("resend")) {
            action = Action.RESEND_REQUESTED;
        } else if (formData.containsKey("cancel")) {
            action = Action.CANCELLED;
        } else {
            action = Action.ANSWERED;
            String otp = formData.getFirst("sms_otp_answer").trim();
            answer = new UserCredentialModel(null, SmsOtpCredentialModel.TYPE, otp);
        }
    }
}
