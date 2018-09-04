package com.blink.atag.tags.builders;

import com.blink.atag.tags.Header;
import com.blink.atag.tags.SimpleATag;

public class HeaderBuilder extends SimpleATagBuilder {

    private Header header;

    @Override
    public void addLine(String line) {
        String[] split = line.split("\\s+", 2);

        if (split.length != 2) {
            header = new Header(line, 1);
            return;
        }
        header = new Header(split[1], split[0].length());
    }

    @Override
    public SimpleATag build() {
        return header;
    }

    @Override
    public boolean isBuilding() {
        return header != null;
    }

    @Override
    public void reset() {
        header = null;
    }
}
