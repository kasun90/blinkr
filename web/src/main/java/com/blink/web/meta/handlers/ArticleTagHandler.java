package com.blink.web.meta.handlers;

import com.blink.common.ArticleHelper;
import com.blink.core.service.Context;
import com.blink.shared.common.Article;
import com.blink.web.meta.MetaTag;
import com.blink.web.meta.PathParams;
import com.blink.web.meta.TagHandler;

import java.util.Map;

public class ArticleTagHandler implements TagHandler {

    private ArticleHelper helper;

    public ArticleTagHandler(Context context) {
        this.helper = new ArticleHelper(context);
    }

    @Override
    public Map<MetaTag, String> handle(PathParams params, Context context, Map<MetaTag, String> tags) throws Exception {
        String key = params.get("key");
        Article article = helper.getDetailsEntity(key);

        if (article == null)
            return tags;

        tags.put(MetaTag.TITLE, article.getTitle());
        tags.put(MetaTag.DESCRIPTION, article.getDescription());
        String coverURL = helper.getCoverURL(key);
        if (coverURL != null)
            tags.put(MetaTag.IMAGE, coverURL);
        return tags;
    }
}
