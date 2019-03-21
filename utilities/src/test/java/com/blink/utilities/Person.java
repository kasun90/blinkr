package com.blink.utilities;


public class Person {
    private String name;
    private int age;
    private transient String thumbnail;

    public Person(String name, int age, String thumbnail) {
        this.name = name;
        this.age = age;
        this.thumbnail = thumbnail;
    }
}
