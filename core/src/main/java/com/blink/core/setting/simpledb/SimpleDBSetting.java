package com.blink.core.setting.simpledb;

import com.blink.core.setting.Setting;

public class SimpleDBSetting implements Setting {
    private String key;
    private String value;

    SimpleDBSetting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
