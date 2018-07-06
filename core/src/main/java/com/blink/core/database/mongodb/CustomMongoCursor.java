package com.blink.core.database.mongodb;

import com.blink.core.database.Cursor;
import com.blink.utilities.BlinkJSON;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.IOException;

public final class CustomMongoCursor<T> implements Cursor<T> {

    private MongoCursor<Document> cursor;
    private Class<T> clazz;

    public CustomMongoCursor(MongoCursor<Document> cursor, Class<T> clazz) {
        this.cursor = cursor;
        this.clazz = clazz;
    }

    @Override
    public boolean hasNext() {
        return cursor.hasNext();
    }

    @Override
    public T next() {
        return BlinkJSON.fromJson(cursor.next().toJson(), clazz);
    }

    @Override
    public void close() throws IOException {
        cursor.close();
    }
}
