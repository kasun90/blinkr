package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;
import java.util.List;

public class Code extends SimpleATag {
    public Code() {
        super(ATagType.CODE);
        set("commands", new LinkedList<SimpleATag>());
    }

    @SuppressWarnings("unchecked")
    public void addCommand(SimpleATag command) {
        ((List<SimpleATag>) get("commands")).add(command);
    }

    @SuppressWarnings("unchecked")
    public int getCommandsCount() {
        return ((List<SimpleATag>) get("commands")).size();
    }

}
