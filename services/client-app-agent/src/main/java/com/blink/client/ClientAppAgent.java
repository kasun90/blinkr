package com.blink.client;

import com.blink.common.*;
import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.messaging.Sender;
import com.blink.core.service.Context;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.client.GenericStatusReplyMessage;
import com.blink.shared.client.album.AlbumDetailsRequestMessage;
import com.blink.shared.client.album.AlbumDetailsResponseMessage;
import com.blink.shared.client.album.AlbumsRequestMessage;
import com.blink.shared.client.album.AlbumsResponseMessage;
import com.blink.shared.client.article.*;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.client.preset.PresetsRequestMessage;
import com.blink.shared.client.preset.PresetsResponseMessage;
import com.blink.shared.client.subscription.NewSubscribeRequestMessage;
import com.blink.shared.client.subscription.UnsubscribeRequestMessage;
import com.blink.shared.common.Article;
import com.blink.shared.common.Subscription;
import com.blink.shared.email.EmailQueueMessage;
import com.blink.shared.email.EmailType;
import com.blink.utilities.BlinkJSON;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientAppAgent extends BlinkService {

    private DBService userMessageDB;
    private AlbumHelper albumHelper;
    private PresetHelper presetHelper;
    private ArticleHelper articleHelper;
    private SubscriptionHelper subscriptionHelper;
    private Sender emailSender;

    public ClientAppAgent(Context context) throws Exception {
        super(context);
        userMessageDB = context.getDbServiceFactory().ofCollection("userMessage");
        userMessageDB.createIndex(false, "timestamp");
        albumHelper = new AlbumHelper(this.getContext());
        presetHelper = new PresetHelper(this.getContext());
        articleHelper = new ArticleHelper(this.getContext());
        subscriptionHelper = new SubscriptionHelper(this.getContext());
        emailSender = getContext().getMessagingService().createSender("email");
    }

    @Subscribe
    public void onClientRequest(ClientRequestMessage requestMessage) throws Exception {
        Object enclosedMessage = requestMessage.getEnclosedMessage();
        setRequestID(requestMessage.getRequestID());

        if (enclosedMessage instanceof UserMessage) {
            onUserMessage((UserMessage) enclosedMessage, requestMessage.getRemoteAddress());
        } else if (enclosedMessage instanceof AlbumsRequestMessage) {
            onAlbumsRequest((AlbumsRequestMessage) enclosedMessage);
        } else if (enclosedMessage instanceof AlbumDetailsRequestMessage) {
            onAlbumDetails((AlbumDetailsRequestMessage) enclosedMessage);
        } else if (enclosedMessage instanceof PresetsRequestMessage) {
            onPresetsRequest(((PresetsRequestMessage) enclosedMessage));
        } else if (enclosedMessage instanceof ArticlesRequestMessage) {
            onArticlesRequest(((ArticlesRequestMessage) enclosedMessage));
        } else if (enclosedMessage instanceof ArticleDetailsRequestMessage) {
            onArticleDetails(((ArticleDetailsRequestMessage) enclosedMessage));
        } else if (enclosedMessage instanceof ArticleViewAckMessage) {
            onArticleViewAck(((ArticleViewAckMessage) enclosedMessage));
        } else if (enclosedMessage instanceof ArticleSearchRequestMessage) {
            onArticleSearch(((ArticleSearchRequestMessage) enclosedMessage));
        } else if (enclosedMessage instanceof NewSubscribeRequestMessage) {
            onNewSubscription(((NewSubscribeRequestMessage) enclosedMessage), requestMessage.getRemoteAddress());
        } else if (enclosedMessage instanceof UnsubscribeRequestMessage) {
            onUnsubscribeRequest(((UnsubscribeRequestMessage) enclosedMessage), requestMessage.getRemoteAddress());
        }
    }

    private void onUserMessage(UserMessage message, String remoteAddress) throws Exception {
        if (validateMessage(message.getRecaptchaToken(), remoteAddress)) {
            message.setTimestamp(BlinkTime.getCurrentTimeMillis());
            userMessageDB.insertOrUpdate(new SimpleDBObject().append("timestamp", message.getTimestamp()),
                    message);
            sendReply(new GenericStatusReplyMessage(true, "Message has been recorded"));
        } else {
            sendReply(new GenericStatusReplyMessage(false, "Message has not been accepted"));
            error("reCaptcha validation failed for message: {}", message);
        }
    }

    private boolean validateMessage(String token, String remoteAddress) throws Exception {
        if (token == null)
            return false;

        CloseableHttpClient client = HttpClientBuilder.create().build();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("secret", "6LdQV38UAAAAACs9GvVg14o3KmJ0f3OH68whbHPS"));
        params.add(new BasicNameValuePair("response", token));
        params.add(new BasicNameValuePair("remoteip", remoteAddress));

        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String resStr = EntityUtils.toString(responseEntity);
            JsonObject jsonObject = BlinkJSON.fromJson(resStr);
            return jsonObject.get("success").getAsBoolean();
        } catch (Exception e) {
            exception("While validating user message", e);
            return false;
        } finally {
            if (response != null)
                response.close();
        }
    }

    private void onAlbumsRequest(AlbumsRequestMessage message) throws Exception {
        sendReply(new AlbumsResponseMessage(albumHelper.getEntities(message.getTimestamp(), message.isLess()
                , message.getLimit())));
    }

    private void onAlbumDetails(AlbumDetailsRequestMessage message) throws Exception {
        sendReply(new AlbumDetailsResponseMessage(albumHelper.getDetailsEntity(message.getKey())));
    }

    private void onPresetsRequest(PresetsRequestMessage message) throws Exception {
        sendReply(new PresetsResponseMessage(presetHelper.getEntities(message.getTimestamp(), message.isLess(),
                message.getLimit())));
    }

    private void onArticlesRequest(ArticlesRequestMessage message) throws Exception {
        SimpleDBObject filter = new SimpleDBObject();
        filter.append("active", true);
        sendReply(new ArticlesResponseMessage(articleHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit(), filter)));
    }

    private void onArticleDetails(ArticleDetailsRequestMessage message) throws Exception {
        Article article = articleHelper.getDetailsEntity(message.getKey());
        if (article != null && !article.isActive())
            article = null;
        sendReply(new ArticleDetailsResponseMessage(article));
    }

    private void onArticleViewAck(ArticleViewAckMessage message) throws Exception {
        articleHelper.incrementViewCount(message.getKey());
        info("Article views incremented [key={}]", message.getKey());
        sendReply(message);
    }

    private void onArticleSearch(ArticleSearchRequestMessage message) throws Exception {
        sendReply(new ArticleSearchResponseMessage(articleHelper.searchArticles(message.getKeyPhrase())));
    }

    private void onNewSubscription(NewSubscribeRequestMessage message, String remoteAddress) throws Exception {
        if (validateMessage(message.getRecaptchaToken(), remoteAddress)) {
            if (message.getEmail() == null || message.getEmail().isEmpty() || message.getFirstName() == null
            || message.getFirstName().isEmpty() || message.getLastName() == null || message.getLastName().isEmpty()) {
                sendReply(new GenericStatusReplyMessage(false, "Invalid values"));
                error("Invalid subscription request: {}", message);
            } else if (!subscriptionHelper.isKeyAvailable(message.getEmail())) {
                sendReply(new GenericStatusReplyMessage(false, "This email already has a subscription"));
            } else if (!EmailValidator.getInstance(true).isValid(message.getEmail())) {
                sendReply(new GenericStatusReplyMessage(false, "This email appears not valid"));
            } else {
                Subscription newSubscription = new Subscription();
                newSubscription.setFirstName(message.getFirstName())
                        .setLastName(message.getLastName())
                        .setEmail(message.getEmail())
                        .setKey(message.getEmail())
                        .setTimestamp(BlinkTime.getCurrentTimeMillis());
                subscriptionHelper.saveEntity(newSubscription);
                sendReply(new GenericStatusReplyMessage(true, "Subscription confirmed!"));
                info("Subscription recorded: {}", message);
                Map<String, String> data = new HashMap<>();
                data.put("name", message.getFirstName());
                emailSender.send(new EmailQueueMessage(EmailType.NEW_SUBSCRIBE, "Welcome To Blink", message.getEmail(), data));
                info("Email queued for new subscription [email={}]", message.getEmail());
            }
        } else {
            sendReply(new GenericStatusReplyMessage(false, "Subscription has not been accepted"));
            error("reCaptcha validation failed for message: {}", message);
        }
    }

    private void onUnsubscribeRequest(UnsubscribeRequestMessage message, String remoteAddress) throws Exception {
        if (validateMessage(message.getRecaptchaToken(), remoteAddress)) {
            if (message.getEmail() == null || message.getEmail().isEmpty()) {
                sendReply(new GenericStatusReplyMessage(false, "Invalid values"));
                error("Invalid unsubscribe request: {}", message);
            } else if (subscriptionHelper.isKeyAvailable(message.getEmail())) {
                sendReply(new GenericStatusReplyMessage(false, "You currently don't have a subscription"));
            } else {
                subscriptionHelper.deleteEntity(message.getEmail());
                sendReply(new GenericStatusReplyMessage(true, "You have unsubscribed to updates"));
                info("User unsubscribed: {}", message);
            }
        } else {
            sendReply(new GenericStatusReplyMessage(false, "Unsubscribe request has not been accepted"));
            error("reCaptcha validation failed for message: {}", message);
        }
    }

    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
