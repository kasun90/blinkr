package com.blink.core.service.impl;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileConfigurationFactory extends ConfigurationFactory {

    public Configuration getConfiguration() {
        Gson gson = new Gson();

        String fileName = "blink.conf";
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> data = gson.fromJson(reader, type);
            return new Configuration(data);
        } catch (Exception e) {
            throw new BlinkRuntimeException(e);
        }
    }
}
