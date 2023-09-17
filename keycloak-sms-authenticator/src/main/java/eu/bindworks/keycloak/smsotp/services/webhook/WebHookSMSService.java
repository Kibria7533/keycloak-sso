package eu.bindworks.keycloak.smsotp.services.webhook;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import eu.bindworks.keycloak.smsotp.services.SmsService;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jboss.logging.Logger;
import org.keycloak.common.util.Base64;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WebHookSMSService implements SmsService {

    private static final Logger logger = Logger.getLogger(WebHookSMSService.class);

    private final KeycloakSession session;
    private final String username;
    private final String password;
    private final String url;
    private final String template;

    public WebHookSMSService(KeycloakSession session, String username, String password, String url, String template) {
        this.session = session;
        this.username = username;
        this.password = password;
        this.url = url;
        this.template = template;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        try {
            HttpClient httpClient = session.getProvider(HttpClientProvider.class).getHttpClient();
            HttpPost httpPost = new HttpPost(url);

            if (StringUtil.isNotBlank(username) && StringUtil.isBlank(password)) {
                final String basicCredentials = String.format("%s:%s", username, password);
                httpPost.setHeader("Authorization", "Basic " + Base64.encodeBytes(basicCredentials.getBytes()));
            } else if (StringUtil.isNotBlank(username)) {
                httpPost.setHeader("Authorization", username);
            }

            if (template.startsWith("{")) {
                String substitutedTemplate = template
                        .replace("*PHONENUMBER*", new String(JsonStringEncoder.getInstance().quoteAsString(phoneNumber)))
                        .replace("*MESSAGE*", new String(JsonStringEncoder.getInstance().quoteAsString(message)));
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                httpPost.setEntity(new StringEntity(substitutedTemplate, ContentType.APPLICATION_JSON));
            } else {
                String substitutedTemplate = template
                        .replace("*PHONENUMBER*", URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8))
                        .replace("*MESSAGE*", URLEncoder.encode(message, StandardCharsets.UTF_8));
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
                httpPost.setEntity(new StringEntity(substitutedTemplate, ContentType.APPLICATION_FORM_URLENCODED));
            }

            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                response.getEntity().writeTo(outputStream);
                String error = "Status: " + response.getStatusLine() + ", Response: " + outputStream;
                logger.error(error);
                throw new RuntimeException("SMS server responded with: " + error);
            }

            logger.tracef("SMS message sent to number %s with response %d...", phoneNumber, response.getStatusLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
