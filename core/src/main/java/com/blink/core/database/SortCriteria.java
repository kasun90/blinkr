package com.blink.core.database;

public class SortCriteria {
    private boolean ascending;
    private String fieldName;

    public SortCriteria(boolean ascending, String fieldName) {
        this.ascending = ascending;
        this.fieldName = fieldName;
    }

    public boolean isAscending() {
        return ascending;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static SortCriteria ascending(String fieldName) {
        return new SortCriteria(true, fieldName);
    }

    public static SortCriteria descending(String fieldName) {
        return new SortCriteria(false, fieldName);
    }
}
