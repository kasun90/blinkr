package com.blink.runner;

import com.blink.core.log.CustomConfigurationFactory;
import com.blink.core.service.BaseService;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.blink.core.service.Context;
import com.blink.core.service.impl.FileConfigurationFactory;
import com.blink.core.system.SystemService;
import com.blink.web.WebServer;
import com.blink.web.vertx.VertxWebServer;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;

public class Bootstrap {

    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        org.apache.logging.log4j.core.config.ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());
    }


    public void start() throws Exception {

        Logger logger = LogManager.getLogger(this.getClass().getName());

        logger.info("Starting system");
        logger.info("Starting {}", "Kasun");

        ConfigurationFactory.setFactory(new FileConfigurationFactory());

        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();
        EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());

        Context.ContextBuilder builder = new Context.ContextBuilder();
        builder.setConfiguration(configuration);
        builder.setEventBus(eventBus);
        Context context = builder.build();

        BaseService system = new SystemService(context);
        eventBus.register(system);

        WebServer server = new VertxWebServer(context);
        server.initialize();
        server.start();
    }
}
