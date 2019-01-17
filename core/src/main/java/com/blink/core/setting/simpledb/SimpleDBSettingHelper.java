package com.blink.core.setting.simpledb;

import com.blink.core.database.DBServiceFactory;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.setting.Setting;
import com.blink.core.setting.SettingHelper;

import java.util.LinkedList;
import java.util.List;

public class SimpleDBSettingHelper extends SimpleDBSettingBase implements SettingHelper {

    public SimpleDBSettingHelper(DBServiceFactory dbServiceFactory) {
        super(dbServiceFactory);
    }

    @Override
    public void store(String key, String value) throws Exception {
        SimpleDBSetting setting = new SimpleDBSetting(key, value);
        dbService.insertOrUpdate(new SimpleDBObject().append("key", setting.getKey()), setting);
    }

    @Override
    public List<Setting> getSettings() throws Exception {
        List<Setting> settings = new LinkedList<>();
        dbService.findAll(SimpleDBSetting.class).forEach(settings::add);
        return settings;
    }

    @Override
    public Setting getSetting(String key) throws Exception {
        return dbService.find(new SimpleDBObject().append("key", key), SimpleDBSetting.class).first();
    }

    @Override
    public void deleteSetting(String key) throws Exception {
        dbService.delete(new SimpleDBObject().append("key", key), SimpleDBSetting.class);
    }
}
