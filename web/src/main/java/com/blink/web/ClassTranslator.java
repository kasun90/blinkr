package com.blink.web;

import com.blink.core.system.ObjectCodec;
import com.blink.utilities.BlinkJSON;
import com.google.gson.JsonObject;

public class ClassTranslator implements ObjectCodec {

    @Override
    public Object fromPayload(String payload) throws Exception {
        JsonObject object = BlinkJSON.fromJson(payload);
        String type = object.get("_type").getAsString();
        Class<?> aClass = Class.forName(type);
        return BlinkJSON.fromJson(object, aClass);
    }

    @Override
    public String toPayload(Object object) throws Exception {
        JsonObject jsonObject = BlinkJSON.toJsonTree(object);
        jsonObject.addProperty("_type", object.getClass().getName());
        return BlinkJSON.toJSON(jsonObject);
    }
}
