package com.blink.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BlinkJSON {
    private static Gson gson = new Gson();
    private static Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static String toPrettyJSON(Object object) {
        return prettyJson.toJson(object);
    }
}
