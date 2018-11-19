package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;
import java.util.List;

public class Terminal extends SimpleATag {
    public Terminal() {
        super(ATagType.TERMINAL);
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

    public void setPrompt(String prompt) {
        set("prompt", prompt);
    }
}
