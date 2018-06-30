package com.blink.core.database;

import java.util.List;

public interface DBOperation {
    DBService withCollection(String collection);
    DBService withDatabase(String database);
    void insert(Object object) throws Exception;
    void update(SimpleDBObject toFind, Object toUpdate) throws Exception;
    long delete(SimpleDBObject toDelete) throws Exception;
    <T> List<T> findAll(Class<T> clazz) throws Exception;
    <T> List<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception;
}
