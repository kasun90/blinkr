package com.blink.core;

import com.blink.core.log.CustomConfigurationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

public class Test {

    static {
        ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());
    }


    public static void main(String[] args) {

        Logger logger = LogManager.getLogger();

        logger.error("Ane");
        logger.info("this is info");
    }
}
