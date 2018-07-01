package com.blink.web;

import com.blink.core.log.CustomConfigurationFactory;
import com.google.common.eventbus.EventBus;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationFactory;


public class Test {

    static {
        ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());
    }


    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        logger.error("Hello");


        Vertx.vertx().deployVerticle(new TestVerticle());


        EventBus eventBus = new EventBus();

    }

}
