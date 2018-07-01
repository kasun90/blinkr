package com.blink.core.service.impl;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class FileConfigurationFactory extends ConfigurationFactory {

    public Configuration getConfiguration() {
        Gson gson = new Gson();


        String fileName = "blink.conf";
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> data = gson.fromJson(reader, type);
            return new Configuration(data);
        } catch (Exception e) {
            throw new BlinkRuntimeException(e);
        }
    }
}
