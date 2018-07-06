package com.blink.core.database;

import java.util.List;

public interface DBOperation {
    DBService withCollection(String collection);
    DBService withDatabase(String database);
    void insert(Object object) throws Exception;
    void update(SimpleDBObject toFind, Object toUpdate) throws Exception;
    void insertOrUpdate(SimpleDBObject toFind, Object toUpdate) throws Exception;
    long delete(SimpleDBObject toDelete) throws Exception;
    <T> DBCollection<T> findAll(Class<T> clazz) throws Exception;
    <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception;
    long getCounterValue() throws Exception;
    long getCounterValue(String name) throws Exception;
}
