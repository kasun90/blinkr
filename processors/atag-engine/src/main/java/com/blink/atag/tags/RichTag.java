package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;
import java.util.List;

public class RichTag extends SimpleATag {
    RichTag(ATagType type) {
        super(type);
        set("children", new LinkedList<SimpleATag>());
    }

    @SuppressWarnings("unchecked")
    public void addChild(SimpleATag tag) {
        ((java.util.List<SimpleATag>) get("children")).add(tag);
    }

    @SuppressWarnings("unchecked")
    public int getChildrenLength() {
        return ((List<SimpleATag>) get("children")).size();
    }
}
