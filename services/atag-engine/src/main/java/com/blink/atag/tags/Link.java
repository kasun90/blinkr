package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Link extends SimpleATag {

    public Link(String value, String url) {
        super(ATagType.LINK);
        set("value", value);
        set("url", url);
    }
}
