package com.blink.core.database.mongodb;

import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.Context;
import com.blink.utilities.BlinkJSON;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
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

    public MongoDBService(Context context) {
        super(context);
        mongoClient = new MongoClient(context.getConfiguration().getDBHost(), context.getConfiguration().getDBPort());
        this.db = mongoClient.getDatabase(context.getConfiguration().getDBName());
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
    public long delete(SimpleDBObject toDelete) throws Exception {
        return coll.deleteOne(MongoDBFilterConverter.fromBlink(toDelete)).getDeletedCount();
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) throws Exception {
        List<T> list = new LinkedList<>();

        try (MongoCursor<Document> iterator = coll.find().iterator()) {
            while (iterator.hasNext()) {
                list.add(BlinkJSON.fromJson(iterator.next().toJson(), clazz));
            }
        }

        return list;
    }

    @Override
    public <T> List<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception {
        List<T> list = new LinkedList<>();

        try (MongoCursor<Document> iterator = coll.find(MongoDBFilterConverter.fromBlink(toFind)).iterator()) {
            while (iterator.hasNext()) {
                list.add(BlinkJSON.fromJson(iterator.next().toJson(), clazz));
            }
        }

        return list;
    }
}
