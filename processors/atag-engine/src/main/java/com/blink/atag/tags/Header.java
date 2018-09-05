package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

public class Header extends SimpleATag {
    public Header(String value, int size) {
        super(ATagType.HEADER);
        set("value", value);
        set("size", size);
    }
}
