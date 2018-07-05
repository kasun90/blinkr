package com.blink.runner;

import com.blink.client.ClientAppAgent;
import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;
import com.blink.core.log.apache.ApacheLog4jLoggerFactory;
import com.blink.core.service.BaseService;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.blink.core.service.Context;
import com.blink.core.service.impl.FileConfigurationFactory;
import com.blink.core.system.SystemService;
import com.blink.core.transport.Bus;
import com.blink.core.transport.google.GoogleEventBus;
import com.blink.web.WebServer;
import com.blink.web.vertx.VertxWebServer;

public class Bootstrap {


    public void start() throws Exception {

        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        Logger bootLogger = loggerFactory.getLogger();
        bootLogger.info("Starting system");

        ConfigurationFactory.setFactory(new FileConfigurationFactory());
        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();
        bootLogger.info("Building configuration complete");

        Bus bus = new GoogleEventBus();

        Context.ContextBuilder builder = new Context.ContextBuilder();
        Context context = builder.setConfiguration(configuration)
                .setBus(bus)
                .setLoggerFactory(loggerFactory)
                .build();

        bootLogger.info("Registering services");
        BaseService system = new SystemService(context);
        context.getBus().register(system);

        //register Services
        context.getBus().register(new ClientAppAgent(context));

        WebServer server = new VertxWebServer(context);
        server.initialize();
        server.start();
        bootLogger.info("System is ready");
    }
}
