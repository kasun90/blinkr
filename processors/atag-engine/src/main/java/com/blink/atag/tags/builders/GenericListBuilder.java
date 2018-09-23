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
        String[] split = line.split("\\s+", 2);
        if (split.length != 2)
            list.addChild(new Text(line));
        else
            list.addChild(new Text(split[1]));
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
