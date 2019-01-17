package com.blink.core.setting;

import com.blink.core.database.mongodb.MongoDBServiceFactory;
import com.blink.core.setting.simpledb.SimpleDBSettingReader;
import com.blink.core.setting.simpledb.SimpleDBSettingHelper;
import org.junit.Before;
import org.junit.Test;

public class SettingTest {
    SettingReader settingReader;
    SettingHelper settingHelper;

    @Before
    public void init() {
        MongoDBServiceFactory factory = new MongoDBServiceFactory("localhost", 27017, "test");
        settingReader = new SimpleDBSettingReader(factory);
        settingHelper = new SimpleDBSettingHelper(factory);
    }

    @Test
    public void settingWriteTest() throws Exception {
        settingHelper.store("mysetting", "Kasun");
        settingHelper.store("double", "3.00");
    }

    @Test
    public void settingReadTest() throws Exception {
        System.out.println(settingReader.getSetting("double"));
    }

    @Test
    public void settingReadWithDefault() throws Exception {
        System.out.println(settingReader.getSetting("mysetting", "hello"));
    }
}
