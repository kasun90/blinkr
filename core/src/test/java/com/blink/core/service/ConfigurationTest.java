package com.blink.core.service;

import com.blink.core.service.impl.FileConfigurationFactory;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {

    @Before
    public void setFactory() {
        ConfigurationFactory.setFactory(new FileConfigurationFactory(""));
    }

    @Test
    public void configurationTest() {
        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();
        String subValue = configuration.getSubValue("service", "names", "hello");
        System.out.println(subValue);

    }
}