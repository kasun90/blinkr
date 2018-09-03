package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Note extends SimpleATag {
    public Note(String value) {
        super(ATagType.NOTE);
        set("value", value);
    }
}
