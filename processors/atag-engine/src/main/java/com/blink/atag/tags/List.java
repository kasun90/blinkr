package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;

public class List extends SimpleATag {
    public List() {
        this(ATagType.LIST);
    }

    public List(ATagType type) {
        super(type);
        set("children", new LinkedList<SimpleATag>());
    }

    public void addChild(SimpleATag tag) {
        ((java.util.List<SimpleATag>) get("children")).add(tag);
    }
}
