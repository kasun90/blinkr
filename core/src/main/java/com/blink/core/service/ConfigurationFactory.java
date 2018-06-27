package com.blink.core.service;

public abstract class ConfigurationFactory {
    private static ConfigurationFactory factory;

    public static void setFactory(ConfigurationFactory newFactory) {
        factory = newFactory;
    }

    public static ConfigurationFactory getFactory() {
        return factory;
    }

    public abstract Configuration getConfiguration();
}
