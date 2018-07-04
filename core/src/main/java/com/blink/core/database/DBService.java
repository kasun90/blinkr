package com.blink.core.database;

import com.blink.core.service.Context;

public abstract class DBService implements DBOperation {
    protected String database;
    protected String collection;
    protected Context context;

    public DBService(String database, String collection) {
        this.database = database;
        this.collection = collection;
    }

    public DBService(Context context) {
        this.database = context.getConfiguration().getDBName();
        this.collection = "common";
    }

}
