package com.blink.common;

import com.blink.atag.ATagEngineImpl;
import com.blink.atag.AtagEngine;
import com.blink.core.database.Filter;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.Context;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.article.RawArticle;
import com.blink.shared.article.ATagType;
import com.blink.shared.common.Article;
import com.blink.shared.common.File;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ArticleHelper extends CommonHelper<Article> {


    public ArticleHelper(Context context) {
        super(context, "articles", context.getFileService().newFileURI("articles").build()
                , Article.class);
    }

    @Override
    public Article fillEntity(Article article) throws Exception {
        if (article.getTags() == null)
            return article;
        article.getTags().stream().filter(aTag -> aTag.getType() == ATagType.IMAGE)
                .forEach(aTag -> {
                    try {
                        aTag.getData().put("url", fileService.getURL(((String) aTag.getData().get("resource"))).toString());
                    } catch (Exception e) {
                        logger.exception("", e);
                    }
                });
        UserDetails user = getUser(article.getAuthor());
        if (user != null)
            article.setAuthor(user.getName());
        return article;
    }

    private UserDetails getUser(String username) throws Exception {
        return context.getDbServiceFactory().ofCollection("adminUser").
                find(new SimpleDBObject().append("username", username), UserDetails.class).first();
    }

    @Override
    public void cleanupEntityGarbage(String key) throws Exception {
        List<String> paths = fileService.listFilePaths(fileService.newFileURI(entityBase).appendResource(key).build());
        for (String path : paths) {
            fileService.delete(path);
        }
        paths = fileService.listFilePaths(fileService.newFileURI(entityBase).appendResource(key).appendResource("cover").build());
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

    public void incrementViewCount(String key) throws Exception {
        Article entity = getEntity(key);

        if (entity == null)
            return;
        entity.setViews(entity.getViews() + 1);
        saveEntity(entity);
    }

    public boolean toggleArticleState(String key, boolean active) throws Exception {
        Article entity = getEntity(key);
        if (entity == null)
            return false;
        entity.setActive(active);
        saveEntity(entity);
        return true;
    }

    private void saveRawArticle(RawArticle rawArticle) throws Exception {
        entityDB.insertOrUpdate(new SimpleDBObject().append("key", rawArticle.getKey()), rawArticle);
    }

    public File saveArticleImage(String key, String fileContent, String fileName) throws Exception {
        logger.info("Article image save request [key={}]", key);
        return saveArticleResource(key, fileContent,
                fileService.newFileURI(entityBase).appendResource(key).appendResource(fileName).build());
    }

    public File saveArticleCover(String key, String fileContent, String fileName) throws Exception {
        logger.info("Article cover save request [key={}]", key);
        String coverBase = fileService.newFileURI(entityBase).appendResource(key).appendResource("cover").build();
        List<String> covers = fileService.listFilePaths(coverBase);
        for (String cover : covers) {
            fileService.delete(cover);
        }

        return saveArticleResource(key, fileContent, fileService.newFileURI(coverBase).appendResource(fileName).build());
    }

    public String getCoverURL(String key) throws Exception {
        String coverBase = fileService.newFileURI(entityBase).appendResource(key).appendResource("cover").build();
        List<String> covers = fileService.listFilePaths(coverBase);
        if (covers.isEmpty())
            return null;
        else
            return fileService.getURL(covers.get(0)).toString();
    }

    private File saveArticleResource(String key, String fileContent, String path) throws Exception {
        Article article = getEntity(key);

        if (article == null) {
            logger.error("No article found to save resource [key={}]", key);
            return null;
        }

        uploadFile(path, fileContent);
        return new File(path, fileService.getURL(path).toString());
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

    public List<Article> searchArticles(String keyPhrase) throws Exception {
        return searchEntities(new SimpleDBObject().append("title", keyPhrase, Filter.CT_CI));
    }
}
