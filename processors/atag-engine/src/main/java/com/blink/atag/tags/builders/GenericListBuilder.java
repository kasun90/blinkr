package com.blink.atag.tags.builders;

import com.blink.atag.tags.List;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;

public abstract  class GenericListBuilder extends SimpleATagBuilder {

    private List list;
    private RichTextBuilder builder;
    private Class<? extends List> type;

    GenericListBuilder(Class<? extends List> type) {
        this.type = type;
        builder = new RichTextBuilder();
    }

    @Override
    public void initNew() throws Exception {
        super.initNew();
        this.list = (List) type.getDeclaredConstructors()[0].newInstance();
    }

    @Override
    public void addLine(String line) {
        String[] split = line.split("\\s+", 2);
        if (split.length != 2)
            list.addChild(new Text(line));
        else {
            builder.addLine(split[1]);
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
