package com.blink.core.database.mongodb;

import com.blink.core.database.Cursor;
import com.blink.utilities.BlinkJSON;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.json.Converter;
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;

import java.io.IOException;

public final class CustomMongoCursor<T> implements Cursor<T> {

    private MongoCursor<Document> cursor;
    private Class<T> clazz;
    private JsonWriterSettings settings;

    public CustomMongoCursor(MongoCursor<Document> cursor, Class<T> clazz) {
        this.cursor = cursor;
        this.clazz = clazz;
        settings = JsonWriterSettings.builder().int64Converter((aLong, strictJsonWriter) -> strictJsonWriter.writeNumber(aLong.toString())).build();
    }

    @Override
    public boolean hasNext() {
        return cursor.hasNext();
    }

    @Override
    public T next() {
        return BlinkJSON.fromJson(cursor.next().toJson(settings), clazz);
    }

    @Override
    public void close() throws IOException {
        cursor.close();
    }
}
