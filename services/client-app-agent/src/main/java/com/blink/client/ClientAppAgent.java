package com.blink.client;

import com.blink.common.AlbumHelper;
import com.blink.common.ArticleHelper;
import com.blink.common.PresetHelper;
import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.client.GenericReplyMessage;
import com.blink.shared.client.album.AlbumDetailsRequestMessage;
import com.blink.shared.client.album.AlbumDetailsResponseMessage;
import com.blink.shared.client.album.AlbumsRequestMessage;
import com.blink.shared.client.album.AlbumsResponseMessage;
import com.blink.shared.client.article.ArticleDetailsRequestMessage;
import com.blink.shared.client.article.ArticleDetailsResponseMessage;
import com.blink.shared.client.article.ArticlesRequestMessage;
import com.blink.shared.client.article.ArticlesResponseMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.client.preset.PresetsRequestMessage;
import com.blink.shared.client.preset.PresetsResponseMessage;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;

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
            onUserMessage((UserMessage) enclosedMessage);
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
        }
    }

    private void onUserMessage(UserMessage message) throws Exception {
        message.setTimestamp(BlinkTime.getCurrentTimeMillis());
        userMessageDB.insertOrUpdate(new SimpleDBObject().append("timestamp", message.getTimestamp()),
                message);
        sendReply(new GenericReplyMessage("Message recorded"));
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
        sendReply(new ArticlesResponseMessage(articleHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit())));
    }

    private void onArticleDetails(ArticleDetailsRequestMessage message) throws Exception {
        sendReply(new ArticleDetailsResponseMessage(articleHelper.getDetailsEntity(message.getKey())));
    }

    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
