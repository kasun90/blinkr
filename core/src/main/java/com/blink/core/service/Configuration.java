package com.blink.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Configuration {
    private Map<String, String> data = new HashMap<>();

    public Configuration() {
    }

    public Configuration(Map<String, String> data) {
        this.data = data;
    }

    public String getDBDriver() {
        return data.get("dbDriver");
    }

    public String getClientPort() {
        return data.get("clientPort");
    }

    public String getAdminPort() {
        return data.get("adminPort");
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
