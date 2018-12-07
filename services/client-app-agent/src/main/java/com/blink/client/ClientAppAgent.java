package com.blink.client;

import com.blink.common.AlbumHelper;
import com.blink.common.ArticleHelper;
import com.blink.common.PresetHelper;
import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.BaseService;
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
import com.blink.shared.common.Article;
import com.blink.utilities.BlinkJSON;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
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
import java.util.List;

public class ClientAppAgent extends BaseService {

    private DBService userMessageDB;
    private AlbumHelper albumHelper;
    private PresetHelper presetHelper;
    private ArticleHelper articleHelper;

    public ClientAppAgent(Context context) throws Exception {
        super(context);
        userMessageDB = context.getDbServiceFactory().ofCollection("userMessage");
        userMessageDB.createIndex(false, "timestamp");
        albumHelper = new AlbumHelper(this.getContext());
        presetHelper = new PresetHelper(this.getContext());
        articleHelper = new ArticleHelper(this.getContext());
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
            String resStre = EntityUtils.toString(responseEntity);
            JsonObject jsonObject = BlinkJSON.fromJson(resStre);
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

    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
