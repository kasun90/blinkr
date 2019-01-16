package com.blink.core.setting.simpledb;

import com.blink.core.database.DBServiceFactory;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.setting.SettingWriter;

public class SimpleDBSettingWriter extends SimpleDBSettingBase implements SettingWriter {

    public SimpleDBSettingWriter(DBServiceFactory dbServiceFactory) {
        super(dbServiceFactory);
    }

    @Override
    public void store(String key, String value) throws Exception {
        SimpleDBSetting setting = new SimpleDBSetting(key, value);
        dbService.insertOrUpdate(new SimpleDBObject().append("key", setting.getKey()), setting);
    }
}
