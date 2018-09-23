package com.blink.atag.tags.builders;

import com.blink.atag.tags.List;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;

public class GenericListBuilder<T extends List> extends SimpleATagBuilder {

    private T list;

    public void initNew(T list) {
        this.list = list;
    }

    @Override
    public void addLine(String line) {
        list.addChild(new Text(line));
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
