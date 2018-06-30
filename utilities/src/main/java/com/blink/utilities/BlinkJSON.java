package com.blink.utilities;

import com.google.gson.Gson;

public class BlinkJSON {
    private static Gson gson = new Gson();

    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }
}
