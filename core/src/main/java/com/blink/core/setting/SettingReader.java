package com.blink.core.setting;

import com.blink.core.service.DerivedService;

public interface SettingReader extends DerivedService {
    String getSetting(String key) throws Exception;
    String getSetting(String key, String defaultValue) throws Exception;
}
