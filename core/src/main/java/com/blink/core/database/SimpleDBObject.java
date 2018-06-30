package com.blink.core.database;

import java.util.HashMap;
import java.util.Map;

public class SimpleDBObject {
    private Map<String, FilterPair> data;

    public SimpleDBObject(Map<String, FilterPair> data) {
        this.data = data;
    }

    public SimpleDBObject() {
        this.data = new HashMap<>();
    }

    public SimpleDBObject append(String key, Object value) {
        data.put(key, new FilterPair(value, Filter.EQ));
        return this;
    }

    public SimpleDBObject append(String key, Object value, Filter filter) {
        data.put(key, new FilterPair(value, filter));
        return this;
    }

    public Map<String, FilterPair> getData() {
        return data;
    }
}
