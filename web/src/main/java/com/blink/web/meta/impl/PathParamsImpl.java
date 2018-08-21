package com.blink.web.meta.impl;

import com.blink.web.meta.PathParams;

import java.util.HashMap;
import java.util.Map;

public class PathParamsImpl implements PathParams {
    private Map<String, String> data = new HashMap<>();

    @Override
    public PathParams add(String key, String value) {
        data.put(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return data.get(key);
    }
}
