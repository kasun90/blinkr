package com.blink.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Configuration {
    private Map<String, Object> data = new HashMap<>();

    public Configuration() {
    }

    public Configuration(Map<String, Object> data) {
        this.data = data;
    }

    public String getDBDriver() {
        return String.class.cast(data.get("dbDriver"));
    }

    public int getClientPort() {
        return Integer.parseInt(String.class.cast(data.get("clientPort")));
    }

    public int getAdminPort() {
        return Integer.parseInt(String.class.cast(data.get("adminPort")));
    }

    public String getDBHost() { return String.class.cast(data.get("dbHost"));}

    public int getDBPort() { return Integer.parseInt(String.class.cast(data.get("dbPort")));}

    public String getDBName() { return String.class.cast(data.get("dbName"));}

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
