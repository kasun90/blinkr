package com.blink.core.setting.simpledb;

import com.blink.core.database.DBService;
import com.blink.core.database.DBServiceFactory;

public abstract class SimpleDBSettingBase {
    protected DBService dbService;

    public SimpleDBSettingBase(DBServiceFactory dbServiceFactory) {
        this.dbService = dbServiceFactory.ofCollection("settings");
    }
}
