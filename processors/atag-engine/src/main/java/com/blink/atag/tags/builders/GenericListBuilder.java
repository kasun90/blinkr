package com.blink.atag.tags.builders;

import com.blink.atag.tags.List;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;

public class GenericListBuilder<T extends List> extends SimpleATagBuilder {

    private T list;
    private RichTextBuilder builder;

    public GenericListBuilder() {
        builder = new RichTextBuilder();
    }

    public void initNew(T list) {
        this.list = list;
    }

    @Override
    public void addLine(String line) {
        String[] split = line.split("\\s+", 2);
        if (split.length != 2)
            list.addChild(new Text(line));
        else {
            builder.addLine(line);
            list.addChild(builder.build());
            builder.reset();
        }

    }

    @Override
    public SimpleATag build() {
        return list;
    }

    @Override
    public boolean isBuilding() {
        return list != null;
    }

    @Override
    public void reset() {
        list = null;
    }
}
