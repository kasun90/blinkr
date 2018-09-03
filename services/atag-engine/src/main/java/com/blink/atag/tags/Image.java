package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Image extends SimpleATag {
    public Image(String resource, String url) {
        super(ATagType.IMAGE);
        set("resource", resource);
        set("url", url);
    }
}
