package com.blink.atag.tags;

import com.blink.shared.article.ATag;
import com.blink.shared.article.ATagType;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleATag extends ATag {
    SimpleATag(ATagType type) {
        super(type, new HashMap<>());
    }

    void set(String key, Object value) {
        getData().put(key, value);
    }

    Object get(String key) {
        return getData().get(key);
    }

    void setTagType(ATagType type) {
        setType(type);
    }
}
