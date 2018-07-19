package com.blink.core.database.mongodb;

import com.blink.core.database.DBService;
import com.blink.core.database.DBServiceFactory;
import com.blink.core.service.Configuration;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBServiceFactory implements DBServiceFactory {
    private MongoClient client;
    private String database;
    private MongoDatabase db;

    public MongoDBServiceFactory(Configuration configuration) {
        this.client = new MongoClient(configuration.getDBHost(), configuration.getDBPort());
        this.database = configuration.getDBName();
        db = client.getDatabase(this.database);
    }

    public MongoDBServiceFactory(String host, int port, String database) {
        this.client = new MongoClient(host, port);
        this.database = database;
        db = client.getDatabase(this.database);
    }

    @Override
    public DBService ofCollection(String collection) {
        return new MongoDBService(database, collection, this.db.getCollection(collection));
    }

    @Override
    public DBService ofDatabase(String database) {
        return new MongoDBService(database, "common", client.getDatabase(database).getCollection("common"));
    }

    @Override
    public DBService ofDatabase(String database, String collection) {
        return new MongoDBService(database, collection, client.getDatabase(database).getCollection(collection));
    }
}
