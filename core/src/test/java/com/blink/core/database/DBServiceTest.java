package com.blink.core.database;

import com.blink.core.database.mongodb.MongoDBService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DBServiceTest {

    DBService dbService;

    @Before
    public void initService() {
        dbService = new MongoDBService("test", "testcollection");
    }


    @Test
    public void inserTest() throws Exception {
        Person person = new Person("Kasun", 28, "Nugegoda");
        dbService.insert(person);
    }


    @Test
    public void findAllTest() throws Exception {
        StringBuilder builder = new StringBuilder();
        List<Person> all = dbService.findAll(Person.class);
        all.forEach((person -> {
            builder.append("name: ").append(person.getName()).append(" age: ").append(person.getAge()).
                    append(" city: ").append(person.getCity()).append("\n");
            System.out.println(builder.toString());
            builder.setLength(0);
        }));
    }

    @Test
    public void findTest() throws Exception {
        SimpleDBObject object = new SimpleDBObject().append("name", "Kasun").append("city", "Nugegoda");

        StringBuilder builder = new StringBuilder();
        List<Person> all = dbService.find(object, Person.class);
        all.forEach((person -> {
            builder.append("name: ").append(person.getName()).append(" age: ").append(person.getAge()).
                    append(" city: ").append(person.getCity()).append("\n");
            System.out.println(builder.toString());
            builder.setLength(0);
        }));

    }


    @Test
    public void updateTest() throws Exception {
        SimpleDBObject object = new SimpleDBObject().append("name", "Kasun");

        dbService.update(object, new Person("Kasun Piyumal", 29, "Battaramulla"));
    }

    @Test
    public void deleteTest() throws Exception {
        SimpleDBObject object = new SimpleDBObject().append("age", 29);

        System.out.println("deleted: " + dbService.delete(object));
    }

}