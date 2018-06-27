package com.blink.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

public class Test {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        logger.error("Hello");



    }
}
