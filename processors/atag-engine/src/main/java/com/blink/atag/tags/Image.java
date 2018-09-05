package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Image extends SimpleATag {
    public Image(String url, String caption) {
        super(ATagType.IMAGE);
        set("caption", caption);
        set("url", url);
    }
}
