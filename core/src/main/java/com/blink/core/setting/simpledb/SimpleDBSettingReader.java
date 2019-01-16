package com.blink.core.setting.simpledb;

import com.blink.core.database.DBCollection;
import com.blink.core.database.DBServiceFactory;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.setting.SettingReader;
import com.blink.utilities.BlinkJSON;

import java.text.MessageFormat;

public class SimpleDBSettingReader extends SimpleDBSettingBase implements SettingReader {

    public SimpleDBSettingReader(DBServiceFactory dbServiceFactory) {
        super(dbServiceFactory);
    }

    @Override
    public String getSetting(String key) throws Exception {
        SimpleDBSetting setting = fetchSetting(key);
        if (setting == null)
            throw new BlinkRuntimeException(MessageFormat.format("Setting not available: {0}", key));
        return setting.getValue();
    }

    @Override
    public String getSetting(String key, String defaultValue) throws Exception {
        SimpleDBSetting setting = fetchSetting(key);
        if (setting == null)
            return defaultValue;
        return setting.getValue();
    }


    private SimpleDBSetting fetchSetting(String key) throws Exception {
        return dbService.find(new SimpleDBObject().append("key", key), SimpleDBSetting.class).first();
    }
}
