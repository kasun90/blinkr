package com.blink.core.database;

import java.io.Closeable;
import java.util.Iterator;

public interface Cursor<T> extends Iterator<T>, Closeable {

}
