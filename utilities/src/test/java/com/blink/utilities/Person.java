package com.blink.utilities;

import javafx.util.Pair;

public class Person {
    private String name;
    private int age;
    private transient String thumbnail;
    private Pair<String, String> testPair;

    public Person(String name, int age, String thumbnail, Pair<String, String> testPair) {
        this.name = name;
        this.age = age;
        this.thumbnail = thumbnail;
        this.testPair = testPair;
    }
}
