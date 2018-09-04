package com.blink.atag.tags;

import com.blink.shared.article.ATag;
import com.blink.shared.article.ATagType;

import java.util.LinkedList;
import java.util.List;

public class Paragraph extends SimpleATag {
    public Paragraph() {
        super(ATagType.PARAGRAPH);
        set("children", new LinkedList<SimpleATag>());
    }

    public void addChild(SimpleATag tag) {
        ((List<SimpleATag>) get("children")).add(tag);
    }

    public int getChildrenLength() {
        return ((List<SimpleATag>) get("children")).size();
    }
}
