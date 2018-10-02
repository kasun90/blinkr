package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;

public class List extends RichTag {
    public List() {
        this(ATagType.LIST);
    }

    public List(ATagType type) {
        super(type);
    }
}
