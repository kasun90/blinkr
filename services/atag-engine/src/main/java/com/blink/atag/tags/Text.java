package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Text extends SimpleATag {
    public Text(String value) {
        super(ATagType.TEXT);
        set("value", value);
    }
}
