package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.common.ArticleHelper;
import com.blink.shared.admin.ActionResponseMessage;
import com.blink.shared.admin.FileUploadResponseMessage;
import com.blink.shared.admin.article.*;
import com.blink.shared.common.Article;
import com.blink.shared.common.File;
import com.blink.shared.email.BulkEmailQueueMessage;
import com.blink.shared.email.EmailType;
import com.blink.shared.system.InvalidRequest;
import com.blink.utilities.BlinkTime;
import xyz.justblink.eventbus.Subscribe;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ArticleHandler extends SubHandler {
    private ArticleHelper articleHelper;

    public ArticleHandler(UserHandler userHandler) {
        super(userHandler);
        this.articleHelper = new ArticleHelper(adminService.getContext());
    }

    @Subscribe
    public void handleArticlesReq(ArticlesRequestMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new ArticlesResponseMessage(articleHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), articleHelper.getEntityCount()));
    }

    @Subscribe
    public void handleArticleKeyCheck(ArticleKeyCheckRequestMessage message) throws Exception {
        logger.info("Article key check: {}", message);
        adminService.sendReply(message.getRequestID(), new ArticleKeyCheckResponseMessage(articleHelper.isKeyAvailable(message.getKey())));
    }

    @Subscribe
    public void handleArticleCreate(CreateArticleRequestMessage message) throws Exception {
        logger.info("Article create request received: {}", message);
        Article article = new Article();
        article.setKey(message.getKey())
                .setTitle(message.getTitle())
                .setDescription(message.getDescription())
                .setAuthor(getUser().getUsername())
                .setActive(false)
                .setTimestamp(BlinkTime.getCurrentTimeMillis());
        articleHelper.saveEntity(article);
        adminService.sendReply(message.getRequestID(), new CreateArticleResponseMessage(message.getKey(), true, "Success"));
    }

    @Subscribe
    public void handleArticleDelete(ArticleDeleteMessage message) throws Exception {
        boolean success = articleHelper.deleteEntity(message.getKey());
        adminService.sendReply(message.getRequestID(), new ArticleDeleteResponeMessage(message.getKey(), success));
        logger.info("Article delete status [sucess={} key={}]", success, message.getKey());
    }

    @Subscribe
    public void handleRawArticleReq(RawArticleRequestMessage message) throws Exception {
        RawArticle rawArticle = articleHelper.getRawArticle(message.getKey());
        Object reply;
        if (rawArticle == null)
            reply = new InvalidRequest("Invalid key");
        else
            reply = new RawArticleResponseMessage(rawArticle.getKey(), rawArticle.getTitle(),
                    rawArticle.getContent());
        adminService.sendReply(message.getRequestID(), reply);
    }

    @Subscribe
    public void handleUpdateArticleReq(UpdateArticleRequestMessage message) throws Exception {
        String desc = articleHelper.updateArticle(message.getKey(), message.getContent());
        adminService.sendReply(message.getRequestID(), new UpdateArticleResponseMessage(desc == null, desc));
    }

    @Subscribe
    public void handleArticleImage(ArticleImageUploadMessage message) throws Exception {
        File file = articleHelper.saveArticleImage(message.getKey(), message.getContent(), message.getFileName());
        adminService.sendReply(message.getRequestID(), new FileUploadResponseMessage(file != null, file));
    }

    @Subscribe
    public void handleArticleCoverUpload(ArticleCoverUploadMessage message) throws Exception {
        File file = articleHelper.saveArticleCover(message.getKey(), message.getContent(), message.getFileName());
        adminService.sendReply(message.getRequestID(), new FileUploadResponseMessage(file != null, file));
    }

    @Subscribe
    public void handleArticleActivate(ArticleActivateMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new ActionResponseMessage(articleHelper.toggleArticleState(message.getKey(), true),
                ""));
        logger.info("Article activated for key: {}", message.getKey());

        Article article = articleHelper.getDetailsEntity(message.getKey());
        Map<String, String> data = new HashMap<>();
        data.put("key", article.getKey());
        data.put("article_name", article.getTitle());
        emailSender.send(new BulkEmailQueueMessage(EmailType.NEW_ARTICLE, MessageFormat.format("New Article: {0}", article.getTitle()), data));
        logger.info("Email bulk notification initiated upon activation [key={}]", message.getKey());
    }

    @Subscribe
    public void handleArticleDeactivate(ArticleDeactivateMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new ActionResponseMessage(articleHelper.toggleArticleState(message.getKey(), false),
                ""));
        logger.info("Article deactivated for key: {}", message.getKey());
    }
}
