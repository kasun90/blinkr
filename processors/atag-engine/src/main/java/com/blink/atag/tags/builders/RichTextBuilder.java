package com.blink.atag.tags.builders;

import com.blink.atag.tags.RichText;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;
import com.blink.shared.article.ATagType;

public class RichTextBuilder extends SimpleATagBuilder {

    private RichText richText;
    private StringBuilder builder;
    private boolean strongText = false;

    public RichTextBuilder() {
        builder = new StringBuilder();
        richText = new RichText();
    }

    @Override
    public void addLine(String line) {
        for (char c : line.toCharArray()) {
            if (c == '^') {
                if (strongText) {
                    richText.addChild(new Text(ATagType.STRONG_TEXT, builder.toString()));
                    builder.setLength(0);
                    strongText = false;
                } else {
                    if (builder.length() != 0) {
                        richText.addChild(new Text(builder.toString()));
                        builder.setLength(0);
                    }
                    strongText = true;
                }

                continue;
            }
            builder.append(c);
        }
    }

    @Override
    public SimpleATag build() {
        if (builder.length() != 0)
            richText.addChild(new Text(builder.toString()));
        return richText;
    }

    @Override
    public boolean isBuilding() {
        return richText.getChildrenLength() != 0;
    }

    @Override
    public void reset() {
        richText = new RichText();
    }
}
