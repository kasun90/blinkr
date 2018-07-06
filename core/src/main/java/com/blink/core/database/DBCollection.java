package com.blink.core.database;

public interface DBCollection<T> extends Iterable<T> {
    T first();
}
