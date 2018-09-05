package com.blink.common;

import com.blink.atag.ATagEngineImpl;
import com.blink.atag.AtagEngine;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.Context;
import com.blink.shared.admin.article.RawArticle;
import com.blink.shared.common.Article;

import java.util.List;

public class ArticleHelper extends CommonHelper<Article> {


    public ArticleHelper(Context context) {
        super(context, "articles", context.getFileService().newFileURI("articles").build()
                , Article.class);
    }

    @Override
    public Article fillEntity(Article article) throws Exception {
        return article;
    }

    @Override
    public void cleanupEntityGarbage(String key) throws Exception {
        List<String> paths = fileService.listFilePaths(fileService.newFileURI(entityBase).appendResource(key).build());
        for (String path : paths) {
            fileService.delete(path);
        }
    }

    public RawArticle getRawArticle(String key) throws Exception {
        RawArticle raw = entityDB.find(new SimpleDBObject().append("key", key), RawArticle.class).first();
        if (raw == null) {
            Article article = getEntity(key);
            if (article == null)
                return null;
            raw = new RawArticle(key, article.getTitle(), "");
        }
        return raw;
    }

    private void saveRawArticle(RawArticle rawArticle) throws Exception {
        entityDB.insertOrUpdate(new SimpleDBObject().append("key", rawArticle.getKey()), rawArticle);
    }

    public String updateArticle(String key, String rawContent) throws Exception {
        logger.info("Article update request received [key={}]", key);

        Article article = getEntity(key);

        if (article == null) {
            logger.error("No article found to update");
            return "No Article Found";
        }

        saveRawArticle(new RawArticle(key, article.getTitle(), rawContent));
        logger.info("Raw article updated [key={}]", key);

        AtagEngine atagEngine = new ATagEngineImpl(context);

        try {
            article = atagEngine.process(rawContent, article);
        } catch (Exception e) {
            logger.exception("Exception while processing article", e);
            return "Exception while processing article";
        }

        saveEntity(article);
        return null;
    }
}
