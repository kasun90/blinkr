package com.blink.web.meta;

public interface PathParams {
    PathParams add(String key, String value);
    void put(String key, String value);
    String get(String key);
    void clear();
}
