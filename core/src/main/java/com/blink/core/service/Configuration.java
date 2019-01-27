package com.blink.core.service;

import com.blink.core.exception.BlinkRuntimeException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.MessageFormat;
import java.util.*;

public class Configuration {
    private Map<String, Object> data = new HashMap<>();
    private Gson gson = new Gson();

    public Configuration() {
    }

    public Configuration(Map<String, Object> data) {
        this.data = data;
    }

    public String getDBDriver() {
        return (String) data.get("dbDriver");
    }

    public int getClientPort() {
        return Integer.parseInt((String) data.get("clientPort"));
    }

    public int getAdminPort() {
        return Integer.parseInt((String) data.get("adminPort"));
    }

    public int getStaticPort(){ return Integer.parseInt((String) data.get("staticPort"));}

    public String getDBHost() {
        return (String) data.get("dbHost");
    }

    public int getDBPort() {
        return Integer.parseInt((String) data.get("dbPort"));
    }

    public String getDBName() {
        return (String) data.get("dbName");
    }

    public String getDomain() { return (String) data.get("domain");}

    public Map<String, Object> getData() {
        return data;
    }

    public String getValue(String key) {
        return getValue(key, String.class);
    }

    public String getValue(String key, String defaultVal) {
        return getValue(key, String.class, defaultVal);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type) {
        Object o = data.get(key);
        if (o == null)
            throw new BlinkRuntimeException(MessageFormat.format("Error reading configuration value: {0}", key));
        return (T)o;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type, T defaultVal) {
        Object o = data.get(key);
        if (o == null)
            return defaultVal;
        return (T)o;
    }

    public Set<String> getValues(String key) {
        Object o = data.get(key);
        Set<String> values = new HashSet<>();
        if (o == null)
            throw new BlinkRuntimeException(MessageFormat.format("Error reading configuration value for key \"{0}\"", key));
        JsonElement tree = gson.toJsonTree(o);
        if (!tree.isJsonArray()) {
            return values;
        } else {
            for (JsonElement jsonElement : tree.getAsJsonArray()) {
                values.add(jsonElement.getAsString());
            }
            return values;
        }
    }

    public JsonObject getRawValue(String key) {
        return gson.toJsonTree(data.get(key)).getAsJsonObject();
    }

    public String getSubValue(String key, String subKey) {
        Object o = data.get(key);
        if (o == null)
            throw new BlinkRuntimeException(MessageFormat.format("Error reading configuration value for key \"{0}\"", key));
        JsonElement jsonElement = gson.toJsonTree(o).getAsJsonObject().get(subKey);
        if (jsonElement == null)
            throw new BlinkRuntimeException(MessageFormat.format("Error reading configuration value for key \"{0}\" and subKey \"{1}\"", key, subKey));
        return jsonElement.getAsString();
    }

    public String getSubValue(String key, String subKey, String defautVal) {
        Object o = data.get(key);
        if (o == null)
            return defautVal;
        JsonElement jsonElement = gson.toJsonTree(o).getAsJsonObject().get(subKey);
        if (jsonElement == null)
            return defautVal;
        return jsonElement.getAsString();
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
