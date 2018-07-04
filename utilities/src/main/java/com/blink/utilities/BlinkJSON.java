package com.blink.utilities;

import com.google.gson.*;

import java.io.IOException;

public class BlinkJSON {
    private static Gson gson = new Gson();
    private static Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
    private static GsonBuilder builder = new GsonBuilder();

    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static JsonObject toJsonTree(Object object) {
        return gson.toJsonTree(object).getAsJsonObject();
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> T fromJson(JsonElement element, Class<T> clazz) {return gson.fromJson(element, clazz);};

    public static String toPrettyJSON(Object object) {
        return prettyJson.toJson(object);
    }

    public static JsonObject fromJson(String jsonString) {
        return gson.fromJson(jsonString, JsonObject.class);
    }
}
