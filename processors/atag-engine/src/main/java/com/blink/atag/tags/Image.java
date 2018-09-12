package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Image extends SimpleATag {
    public Image(String resource, String caption) {
        super(ATagType.IMAGE);
        set("caption", caption);
        set("resource", resource);
    }

    public void setURL(String url) {
        set("url", url);
    }

    public String getResource() {
        return ((String) get("resource"));
    }
}
