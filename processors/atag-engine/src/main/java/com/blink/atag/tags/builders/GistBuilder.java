package com.blink.atag.tags.builders;

import com.blink.atag.tags.Gist;
import com.blink.atag.tags.SimpleATag;

public class GistBuilder extends SimpleATagBuilder {

    private String source;
    private String file;

    @Override
    public void addLine(String line) {
        StringBuilder builder = new StringBuilder();
        line = line.replaceAll("\\[gist]", "");
        for (char c : line.toCharArray()) {
            if (c == '(' || c == '[' || c == '!')
                continue;
            else if (c == ')') {
                source = builder.toString();
                builder.setLength(0);
                continue;
            } else if (c == ']') {
                file = builder.toString();
                builder.setLength(0);
                continue;
            }
            builder.append(c);
        }
    }

    @Override
    public SimpleATag build() {
        return new Gist(source, file);
    }

    @Override
    public boolean isBuilding() {
        return source != null;
    }

    @Override
    public void reset() {
        source = null;
    }
}
