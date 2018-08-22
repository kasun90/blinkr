package com.blink.web.meta;

public interface MetaPath {
    static String[] doSegment(String path) {
        if (path.startsWith("/"))
            path = path.substring(1);
        if (path.isEmpty())
            return new String[]{};
        return path.split("/");
    }

    String getPath();

    int getSegmentCount();

    String[] getSegments();

    void handler(TagHandler handler);

    TagHandler getHandler();
}
