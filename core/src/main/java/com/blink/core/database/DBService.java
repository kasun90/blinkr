package com.blink.core.database;

import com.blink.core.service.Configuration;
import com.blink.utilities.BlinkJSON;
import com.google.gson.JsonObject;

public abstract class DBService implements DBOperation {
    protected String database;
    protected String collection;
    protected Configuration configuration;

    public DBService(String database, String collection) {
        this.database = database;
        this.collection = collection;
    }

    public String serialize(Object object) {
        JsonObject jsonObject = BlinkJSON.toJsonTree(object);
        jsonObject.addProperty("_type", object.getClass().getName());
        return BlinkJSON.toJSON(jsonObject);
    }

    public SimpleDBObject modifyQuery(SimpleDBObject object, Class<?> clazz) {
        object.append("_type", clazz.getName());
        return object;
    }

}
