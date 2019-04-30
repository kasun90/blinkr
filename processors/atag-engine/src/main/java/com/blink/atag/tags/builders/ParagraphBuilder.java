package com.blink.atag.tags.builders;

import com.blink.atag.tags.Link;
import com.blink.atag.tags.Paragraph;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;
import com.blink.shared.article.ATagType;

public class ParagraphBuilder extends SimpleATagBuilder {

    private StringBuilder builder;
    private Paragraph paragraph;
    private boolean expectLink = false;
    private boolean strongText = false;
    private String currentLink = null;

    public ParagraphBuilder() {
        builder = new StringBuilder();
        paragraph = new Paragraph();
    }

    @Override
    public void addLine(String line) {
        for (char c : line.toCharArray()) {
            if (c == '[') {
                if (builder.length() != 0) {
                    paragraph.addChild(new Text(builder.toString()));
                    builder.setLength(0);
                }
                continue;
            } else if (c == ']') {
                currentLink = builder.toString();
                builder.setLength(0);
                expectLink = true;
                continue;
            } else if (expectLink) {
                if (c == '(') {
                    continue;
                } else if (c == ')') {
                    String currentURL = builder.toString();
                    builder.setLength(0);
                    paragraph.addChild(new Link(currentLink, currentURL));
                    expectLink = false;
                    currentLink = null;
                    continue;
                }
            } else if (c == '^') {
                if (strongText) {
                    paragraph.addChild(new Text(ATagType.STRONG_TEXT, builder.toString()));
                    builder.setLength(0);
                    strongText = false;
                } else {
                    if (builder.length() != 0) {
                        paragraph.addChild(new Text(builder.toString()));
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
            paragraph.addChild(new Text(builder.toString()));
        return paragraph;
    }

    @Override
    public boolean isBuilding() {
        return paragraph.getChildrenLength() != 0 || builder.length() != 0;
    }

    @Override
    public void reset() {
        expectLink = false;
        strongText = false;
        currentLink = null;
        paragraph = new Paragraph();
        builder.setLength(0);
    }
}
