package com.blink.atag.tags.builders;

public abstract class SimpleATagBuilder implements AtagBuilder {
    private boolean shouldExpect = false;

    public void setExpect(boolean expect) {
        this.shouldExpect = expect;
    }

    public boolean expect() {
        return shouldExpect;
    }
}
