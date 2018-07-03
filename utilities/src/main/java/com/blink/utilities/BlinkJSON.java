package com.blink.utilities;

import com.google.gson.*;

import java.io.IOException;

public class BlinkJSON {
    private static Gson gson = new Gson();
    private static Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static String toJSONWithType(Object object) {
        JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
        jsonObject.addProperty("_type", object.getClass().getName());
        return gson.toJson(jsonObject);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static String toPrettyJSON(Object object) {
        return prettyJson.toJson(object);
    }
}
