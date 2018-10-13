package com.blink.web.meta.handlers;

import com.blink.core.service.Context;
import com.blink.web.meta.MetaTag;
import com.blink.web.meta.PathParams;
import com.blink.web.meta.TagHandler;

import java.util.Map;

public class AssociateHandler implements TagHandler {
    @Override
    public Map<MetaTag, String> handle(PathParams params, Context context, Map<MetaTag, String> tags) throws Exception {
        tags.put(MetaTag.TITLE, "Nubamin on Blink");
        tags.put(MetaTag.DESCRIPTION, "Nubamin A.K.A Pradeep K. Liyanage");
        tags.put(MetaTag.VIDEO, "https://www.youtube.com/v/ePruRYrY1xY");
        return tags;
    }
}
