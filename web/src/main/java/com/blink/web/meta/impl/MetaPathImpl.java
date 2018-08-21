package com.blink.web.meta.impl;

import com.blink.web.meta.MetaPath;
import com.blink.web.meta.TagHandler;

public class MetaPathImpl implements MetaPath {
    private String path;
    private String[] segments;
    private TagHandler handler;

    public MetaPathImpl(String path) {
        this.path = path;
        this.segments = MetaPath.doSegment(path);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getSegmentCount() {
        return this.segments.length;
    }

    @Override
    public void handler(TagHandler handler) {
        this.handler = handler;
    }

    @Override
    public TagHandler getHandler() {
        return handler;
    }
}
