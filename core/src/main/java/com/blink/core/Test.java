package com.blink.core;

import com.blink.core.database.DBService;
import com.blink.core.database.mongodb.MongoDBService;
import com.blink.core.log.CustomConfigurationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

import java.util.List;
import java.util.logging.Level;

public class Test {

    static {
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());

    }


    public static void main(String[] args) throws Exception {

        Logger logger = LogManager.getLogger();

        logger.error("Ane");
        logger.info("this is info");


        DBService dbService;
        dbService = new MongoDBService("test", "testcollection");

        StringBuilder builder = new StringBuilder();
        List<Person> all = dbService.findAll(Person.class);
        all.forEach((person -> {
            builder.append("name: ").append(person.getName()).append(" age: ").append(person.getAge()).
                    append(" city: ").append(person.getCity()).append("\n");
            System.out.println(builder.toString());
            builder.setLength(0);
        }));
    }
}
