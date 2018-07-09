package com.blink.core.database;

import java.util.Spliterator;
import java.util.Spliterators;

public interface DBCollection<T> extends Iterable<T> {
    T first();

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }

}
