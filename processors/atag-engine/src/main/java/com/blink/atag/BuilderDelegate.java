package com.blink.atag;

import com.blink.atag.tags.builders.SimpleATagBuilder;

public interface BuilderDelegate {
    void restoreDefaultActiveBuilder() throws Exception;
    void setActiveBuilder(SimpleATagBuilder builder);
    SimpleATagBuilder getActiveBuilder();
}
