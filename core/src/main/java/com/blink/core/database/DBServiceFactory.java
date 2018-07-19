package com.blink.core.database;

public interface DBServiceFactory {
    DBService ofCollection(String collection);
    DBService ofDatabase(String database);
    DBService ofDatabase(String database, String collection);
}
