package com.blink.core.database;

public enum Filter {
    EQ("Equal"),
    LT("Less Than"),
    LTE("Less Than or Equal"),
    GT("Greater Than"),
    GTE("Greater Than or Equal"),
    CT_CI("Contains, Case Insensitive");

    String description;

    Filter(String description) {
        this.description = description;
    }
}
