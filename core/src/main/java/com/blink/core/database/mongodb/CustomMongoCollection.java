package com.blink.core.database.mongodb;

import com.blink.core.database.DBCollection;
import com.blink.utilities.BlinkJSON;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.util.Iterator;

public final class CustomMongoCollection<T> implements DBCollection<T> {

    private FindIterable<Document> iterable;
    private Class<T> clazz;

    public CustomMongoCollection(FindIterable<Document> iterable, Class<T> clazz) {
        this.iterable = iterable;
        this.clazz = clazz;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomMongoCursor<>(iterable.iterator(), clazz);
    }

    @Override
    public T first() {
        Document first = iterable.first();
        return first == null ? null : BlinkJSON.fromJson(first.toJson(), clazz);
    }
}
