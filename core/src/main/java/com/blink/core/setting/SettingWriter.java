package com.blink.core.setting;

import com.blink.core.service.DerivedService;

public interface SettingWriter extends DerivedService {
    void store(String key, String value) throws Exception;
}
