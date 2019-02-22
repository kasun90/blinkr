package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Gist extends SimpleATag {
    public Gist(String source, String file) {
        super(ATagType.GIST);
        set("source", source);
        set("file", file);
    }
}
