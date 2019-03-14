package com.blink.runner;

import com.blink.admin.AdminAppAgent;
import com.blink.client.ClientAppAgent;
import com.blink.core.database.mongodb.MongoDBServiceFactory;
import com.blink.core.file.local.LocalFileService;
import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;
import com.blink.core.log.apache.ApacheLog4jLoggerFactory;
import com.blink.core.messaging.embed.EmbeddedMessageService;
import com.blink.core.service.Configuration;
import com.blink.core.service.ConfigurationFactory;
import com.blink.core.service.Context;
import com.blink.core.service.impl.FileConfigurationFactory;
import com.blink.core.setting.SettingHelper;
import com.blink.core.setting.SettingReader;
import com.blink.core.setting.simpledb.SimpleDBSettingHelper;
import com.blink.core.setting.simpledb.SimpleDBSettingReader;
import com.blink.core.transport.blinkr.BlinkrBusFactory;
import com.blink.core.transport.blinkr.BlinkrBusService;
import com.blink.email.EmailAgent;
import com.blink.systemagent.SystemService;
import com.blink.web.ClassTranslator;
import com.blink.web.WebServer;
import com.blink.web.vertx.VertxWebServer;

public class Bootstrap {

    void start(String[] args) throws Exception {

        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        Logger bootLogger = loggerFactory.getLogger();
        bootLogger.info("Starting system");

        try {
            String environment = "";
            if (args.length == 0)
                bootLogger.info("System is running on development mode. Please use '--prod' argument to enable production mode");
            else
                environment = args[0].replaceAll("--", "");

            ConfigurationFactory.setFactory(new FileConfigurationFactory(environment));
            Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();
            bootLogger.info("Building configuration complete");

            Context.ContextBuilder builder = new Context.ContextBuilder();
            Context context = builder.setConfiguration(configuration)
                    .setBusService(new BlinkrBusService(new BlinkrBusFactory()))
                    .setLoggerFactory(loggerFactory)
                    .setDbServiceFactory(new MongoDBServiceFactory(configuration))
                    .setFileService(new LocalFileService(configuration))
                    .setMessagingService(new EmbeddedMessageService(new ClassTranslator(), "hawtio/hawtio.war"))
                    .build();

            context.registerDerivedService(SettingReader.class, new SimpleDBSettingReader(context.getDbServiceFactory()));
            context.registerDerivedService(SettingHelper.class, new SimpleDBSettingHelper(context.getDbServiceFactory()));

            bootLogger.info("Registering mandatory services");
            context.getBusService().getDefault().register(new SystemService(context));

            //register Services
            context.getBusService().getDefault().register(new ClientAppAgent(context));
            context.getBusService().getDefault().register(new AdminAppAgent(context));

            bootLogger.info("Starting web server");
            WebServer server = new VertxWebServer(context);
            server.initialize();
            server.start();

            bootLogger.info("Registering complimentary services");
            context.getBusService().getDefault().register(new EmailAgent(context));
            bootLogger.info("System is ready");
        } catch (Exception e) {
            bootLogger.exception("Exception while starting the system", e);
        }
    }
}
