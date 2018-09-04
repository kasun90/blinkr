package com.blink.common;

import com.blink.core.service.Context;
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
}
