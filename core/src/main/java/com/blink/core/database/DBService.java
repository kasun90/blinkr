package com.blink.core.database;

import com.blink.core.service.Configuration;

public abstract class DBService implements DBOperation {
    protected String database;
    protected String collection;
    protected Configuration configuration;

    public DBService(String database, String collection) {
        this.database = database;
        this.collection = collection;
    }

    public DBService(Configuration configuration) {
        this.database = configuration.getDBName();
        this.collection = "common";
    }

}
