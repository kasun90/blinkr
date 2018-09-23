package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Text extends SimpleATag {
    public Text(String value) {
        this(ATagType.TEXT, value);
    }

    public Text(ATagType type, String value) {
        super(type);
        set("value", value);
    }
}
