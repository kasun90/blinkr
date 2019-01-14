package com.blink.core.setting;

public interface SettingReader {
    String getSetting(String key) throws Exception;
    String getSetting(String key, String defaultValue);
    <T> T getSetting(String key, Class<T> clazz);
    <T> T getSetting(String key, T defaultValue, Class<T> clazz);
}
