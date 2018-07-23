package com.blink.core.database.mongodb;

import com.blink.core.database.DBCollection;
import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.database.SortCriteria;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;

public class MongoDBService extends DBService {
    private MongoCollection<Document> coll;

    MongoDBService(String database, String collection, MongoCollection<Document> coll) {
        super(database, collection);
        this.coll = coll;
    }

    @Override
    public void insert(Object object) throws Exception {
        coll.insertOne(Document.parse(serialize(object)));
    }

    @Override
    public void update(SimpleDBObject toFind, Object toUpdate) throws Exception {
        toFind = modifyQuery(toFind, toUpdate.getClass());
        coll.updateMany(MongoDBFilterConverter.toQuery(toFind), new Document("$set", Document.parse(serialize(toUpdate))));
    }

    @Override
    public void insertOrUpdate(SimpleDBObject toFind, Object toUpdate) {
        toFind = modifyQuery(toFind, toUpdate.getClass());
        coll.updateMany(MongoDBFilterConverter.toQuery(toFind), new Document("$set", Document.parse(serialize(toUpdate))),
                new UpdateOptions().upsert(true));
    }

    @Override
    public long delete(SimpleDBObject toDelete, Class<?> clazz) throws Exception {
        toDelete = modifyQuery(toDelete, clazz);
        return coll.deleteMany(MongoDBFilterConverter.toQuery(toDelete)).getDeletedCount();
    }

    @Override
    public <T> DBCollection<T> findAll(Class<T> clazz) throws Exception {
        SimpleDBObject toFind = modifyQuery(new SimpleDBObject(), clazz);
        return new CustomMongoCollection<>(coll.find(MongoDBFilterConverter.toQuery(toFind)), clazz);
    }

    @Override
    public <T> DBCollection<T> findAll(Class<T> clazz, SortCriteria... sorts) {
        SimpleDBObject toFind = modifyQuery(new SimpleDBObject(), clazz);
        return new CustomMongoCollection<>(coll.find(MongoDBFilterConverter.toQuery(toFind))
                .sort(MongoDBFilterConverter.toSortQuery(sorts)), clazz);
    }

    @Override
    public <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz) throws Exception {
        toFind = modifyQuery(toFind, clazz);
        return new CustomMongoCollection<>(coll.find(MongoDBFilterConverter.toQuery(toFind)), clazz);
    }

    @Override
    public <T> DBCollection<T> find(SimpleDBObject toFind, Class<T> clazz, SortCriteria... sorts) {
        toFind = modifyQuery(toFind, clazz);
        return new CustomMongoCollection<>(coll.find(MongoDBFilterConverter.toQuery(toFind))
                .sort(MongoDBFilterConverter.toSortQuery(sorts)), clazz);
    }

    @Override
    public long getCounterValue() {
        return getCounterValue("default");
    }

    @Override
    public long getCounterValue(String name) {
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        options.returnDocument(ReturnDocument.AFTER);
        Document result = coll.findOneAndUpdate(new Document("_name", name), new Document("$inc", new Document("value", 1L)), options);
        return result.getLong("value");
    }

    @Override
    public void createIndex(boolean ascending, String... fieldNames) {
        if (ascending)
            coll.createIndex(Indexes.ascending(fieldNames));
        else
            coll.createIndex(Indexes.descending(fieldNames));
    }

    @Override
    public long count(Class<?> clazz) {
        SimpleDBObject toFind = modifyQuery(new SimpleDBObject(), clazz);
        return coll.count(MongoDBFilterConverter.toQuery(toFind));
    }

    @Override
    public long count(SimpleDBObject toFind, Class<?> clazz) {
        toFind = modifyQuery(toFind, clazz);
        return coll.count(MongoDBFilterConverter.toQuery(toFind));
    }
}
