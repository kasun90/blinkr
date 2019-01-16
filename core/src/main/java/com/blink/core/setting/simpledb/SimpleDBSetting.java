package com.blink.core.setting.simpledb;

public class SimpleDBSetting {
    private String key;
    private String value;

    public SimpleDBSetting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
