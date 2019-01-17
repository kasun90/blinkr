package com.blink.core.setting;

import com.blink.core.service.DerivedService;

import java.util.List;

public interface SettingHelper extends DerivedService {
    void store(String key, String value) throws Exception;
    List<Setting> getSettings() throws Exception;
    Setting getSetting(String key) throws Exception;
    void deleteSetting(String key) throws Exception;
}
