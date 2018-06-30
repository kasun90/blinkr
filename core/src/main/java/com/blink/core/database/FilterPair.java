package com.blink.core.database;

public class FilterPair {
    private Object value;
    private Filter filter;

    public FilterPair(Object value, Filter filter) {
        this.value = value;
        this.filter = filter;
    }

    public Object getValue() {
        return value;
    }

    public Filter getFilter() {
        return filter;
    }
}
