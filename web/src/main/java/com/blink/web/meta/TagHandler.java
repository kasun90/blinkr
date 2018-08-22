package com.blink.web.meta;

import com.blink.core.service.Context;

import java.util.Map;

public interface TagHandler {
    Map<MetaTag, String> handle(PathParams params, Context context, Map<MetaTag, String> tags) throws Exception;
}
