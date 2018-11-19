package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;
import java.util.List;

public class Code extends SimpleATag {
    public Code() {
        super(ATagType.CODE);
        set("lines", new LinkedList<SimpleATag>());
    }

    @SuppressWarnings("unchecked")
    public void addLine(SimpleATag command) {
        ((List<SimpleATag>) get("lines")).add(command);
    }

    @SuppressWarnings("unchecked")
    public int getLineCount() {
        return ((List<SimpleATag>) get("lines")).size();
    }

}
