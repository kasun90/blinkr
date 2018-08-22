package com.blink.web.meta.handlers;

import com.blink.core.service.Context;
import com.blink.web.meta.MetaTag;
import com.blink.web.meta.PathParams;
import com.blink.web.meta.TagHandler;

import java.util.Map;

public class CommonTagHandler implements TagHandler {


    @Override
    public Map<MetaTag, String> handle(PathParams params, Context context, Map<MetaTag, String> tags) throws Exception {
        tags.put(MetaTag.TITLE, "Blink");
        tags.put(MetaTag.DESCRIPTION, "Home of Blink");
        String image = context.getFileService().newFileURI()
                .appendResource("media")
                .appendResource("meta")
                .appendResource("image.png").build();
        tags.put(MetaTag.IMAGE, context.getFileService().getURL(image).toString());
        return tags;
    }
}
