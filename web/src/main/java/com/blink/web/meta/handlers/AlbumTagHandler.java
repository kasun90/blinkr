package com.blink.web.meta.handlers;

import com.blink.common.AlbumHelper;
import com.blink.core.service.Context;
import com.blink.shared.common.Album;
import com.blink.web.meta.MetaTag;
import com.blink.web.meta.PathParams;
import com.blink.web.meta.TagHandler;

import java.util.Map;

public class AlbumTagHandler implements TagHandler {

    private AlbumHelper helper;

    public AlbumTagHandler(Context context) {
        this.helper = new AlbumHelper(context);
    }

    @Override
    public Map<MetaTag, String> handle(PathParams params, Context context, Map<MetaTag, String> tags) throws Exception {
        String key = params.get("key");
        Album album = helper.getDetailsEntity(key);

        if (album == null)
            return tags;

        tags.put(MetaTag.TITLE, album.getTitle());
        tags.put(MetaTag.DESCRIPTION, album.getDescription());
        tags.put(MetaTag.IMAGE, album.getCover().getUrl());
        return tags;
    }
}
