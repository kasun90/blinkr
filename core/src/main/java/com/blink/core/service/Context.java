package com.blink.core.service;

public class Context {
    private Configuration configuration;

    public Context(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
