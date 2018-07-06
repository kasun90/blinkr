package com.blink.core.database.mongodb;

import com.blink.core.database.DBCollection;
import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.Configuration;
import com.blink.core.service.Context;
import com.blink.utilities.BlinkJSON;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public class MongoDBService extends DBService {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> coll;


    public MongoDBService(String database, String collection) {
        super(database, collection);
        mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDatabase(database);
        this.coll = this.db.getCollection(collection);
    }

    public MongoDBService(Configuration configuration) {
        super(configuration);
        mongoClient = new MongoClient(configuration.getDBHost(), configuration.getDBPort());
        this.db = mongoClient.getDatabase(configuration.getDBName());
    }

    @Override
    public DBService withCollection(String collection) {
        this.collection = collection;
        this.coll = this.db.getCollection(collection);
        return this;
    }

    @Override
    public DBService withDatabase(String database) {
        this.database = database;
        this.db = mongoClient.getDatabase(database);
        return this;
    }

    @Override
    public void insert(Object object) throws Exception {
        coll.insertOne(Document.parse(BlinkJSON.toJSON(object)));
    }

    @Override
    public void update(SimpleDBObject toFind, Object toUpdate) throws Exception {
        coll.updateOne(MongoDBFilterConverter.fromBlink(toFind), new Document("$set", Document.parse(BlinkJSON.toJSON(toUpdate))));
    }

    @Override
    public void insertOrUpdate(SimpleDBObject toFind, Object toUpdate) {
        coll.updateOne(MongoDBFilterConverter.fromBlink(toFind), new Document("$set", Document.parse(BlinkJSON.toJSON(toUpdate))),
                new UpdateOptions().upsert(true));
    }

    @Override
    public long delete(SimpleDBObject toDelete) throws Exception {
        return coll.deleteOne(MongoDBFilterConverter.fromBlink(toDelete)).getDeletedCount();
    }

    @Override
    public <T> DBCollection<T> findAll(Class<T> clazz) throws Exception {
        return new CustomMongoCollection<>(coll.find(), clazz);
    }

    @Override
    public <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception {
        return new CustomMongoCollection<>(coll.find(MongoDBFilterConverter.fromBlink(toFind)), clazz);
    }

    @Override
    public long getCounterValue() {
        UpdateOptions updateOptions = new UpdateOptions().upsert(true);
        UpdateResult updateResult = coll.updateOne(null, null, updateOptions);

        return 0;
    }

    @Override
    public long getCounterValue(String name) {
        return 0;
    }
}
