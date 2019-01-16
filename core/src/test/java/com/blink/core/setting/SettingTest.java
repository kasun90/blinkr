package com.blink.core.setting;

import com.blink.core.database.mongodb.MongoDBServiceFactory;
import com.blink.core.setting.simpledb.SimpleDBSettingReader;
import com.blink.core.setting.simpledb.SimpleDBSettingWriter;
import org.junit.Before;
import org.junit.Test;

public class SettingTest {
    SettingReader settingReader;
    SettingWriter settingWriter;

    @Before
    public void init() {
        MongoDBServiceFactory factory = new MongoDBServiceFactory("localhost", 27017, "test");
        settingReader = new SimpleDBSettingReader(factory);
        settingWriter = new SimpleDBSettingWriter(factory);
    }

    @Test
    public void settingWriteTest() throws Exception {
        settingWriter.store("mysetting", "Kasun");
        settingWriter.store("double", "3.00");
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
