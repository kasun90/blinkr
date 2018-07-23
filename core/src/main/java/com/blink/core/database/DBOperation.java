package com.blink.core.database;

import java.util.List;

public interface DBOperation {
    void insert(Object object) throws Exception;
    void update(SimpleDBObject toFind, Object toUpdate) throws Exception;
    void insertOrUpdate(SimpleDBObject toFind, Object toUpdate) throws Exception;
    long delete(SimpleDBObject toDelete, Class<?> clazz) throws Exception;
    <T> DBCollection<T> findAll(Class<T> clazz) throws Exception;
    <T> DBCollection<T> findAll(Class<T> clazz, SortCriteria... sorts) throws Exception;
    <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception;
    <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz, SortCriteria... sorts) throws Exception;
    long getCounterValue() throws Exception;
    long getCounterValue(String name) throws Exception;
    void createIndex(boolean ascending, String... fieldNames) throws Exception;
    long count(Class<?> clazz) throws Exception;
    long count(SimpleDBObject toFind, Class<?> clazz) throws Exception;
}
