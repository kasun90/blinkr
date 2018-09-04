package com.blink.atag.tags.builders;

import com.blink.atag.tags.SimpleATag;

public interface AtagBuilder {
    void addLine(String line);
    SimpleATag build();
    boolean isBuilding();
    void reset();
}
