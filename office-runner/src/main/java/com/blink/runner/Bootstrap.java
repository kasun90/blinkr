package com.blink.runner;

import com.blink.admin.AdminAppAgent;
import com.blink.client.ClientAppAgent;
import com.blink.core.database.DBServiceFactory;
import com.blink.core.database.mongodb.MongoDBServiceFactory;
import com.blink.core.file.FileService;
import com.blink.core.file.local.LocalFileService;
import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;
import com.blink.core.log.apache.ApacheLog4jLoggerFactory;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.blink.core.service.Context;
import com.blink.core.service.impl.FileConfigurationFactory;
import com.blink.core.setting.SettingHelper;
import com.blink.core.setting.SettingReader;
import com.blink.core.setting.simpledb.SimpleDBSettingHelper;
import com.blink.core.setting.simpledb.SimpleDBSettingReader;
import com.blink.core.transport.Bus;
import com.blink.core.transport.google.GoogleEventBus;
import com.blink.systemagent.SystemService;
import com.blink.web.WebServer;
import com.blink.web.vertx.VertxWebServer;

public class Bootstrap {

    void start(String[] args) throws Exception {

        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        Logger bootLogger = loggerFactory.getLogger();
        bootLogger.info("Starting system");

        String environment = "";
        if (args.length == 0)
            bootLogger.info("System is running on development mode. Please use '--prod' argument to enable production mode");
        else
            environment = args[0].replaceAll("--", "");

        ConfigurationFactory.setFactory(new FileConfigurationFactory(environment));
        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();
        bootLogger.info("Building configuration complete");

        Bus bus = new GoogleEventBus();
        DBServiceFactory dbServiceFactory = new MongoDBServiceFactory(configuration);
        FileService fileService = new LocalFileService(configuration);

        Context.ContextBuilder builder = new Context.ContextBuilder();
        Context context = builder.setConfiguration(configuration)
                .setBus(bus)
                .setLoggerFactory(loggerFactory)
                .setDbServiceFactory(dbServiceFactory)
                .setFileService(fileService)
                .build();

        context.registerDerivedService(SettingReader.class, new SimpleDBSettingReader(context.getDbServiceFactory()));
        context.registerDerivedService(SettingHelper.class, new SimpleDBSettingHelper(context.getDbServiceFactory()));

        bootLogger.info("Registering services");
        context.getBus().register(new SystemService(context));

        //register Services
        context.getBus().register(new ClientAppAgent(context));
        context.getBus().register(new AdminAppAgent(context));

        WebServer server = new VertxWebServer(context);
        server.initialize();
        server.start();
        bootLogger.info("System is ready");
    }
}
