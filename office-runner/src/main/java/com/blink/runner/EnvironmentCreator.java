package com.blink.runner;

import com.blink.core.database.DBService;
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
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.UserType;
import com.blink.utilities.BlinkUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class EnvironmentCreator {
    public static void main(String[] args) throws Exception {
        new EnvironmentCreator().create(args);
    }

    private void create(String[] args) throws Exception {
        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        Logger logger = loggerFactory.getLogger("Environment");

        String environment = "";
        if (args.length != 0)
            environment = args[0].replaceAll("--", "");

        ConfigurationFactory.setFactory(new FileConfigurationFactory(environment));
        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();

        DBServiceFactory dbServiceFactory = new MongoDBServiceFactory(configuration);
        FileService fileService = new LocalFileService(configuration);

        Context.ContextBuilder builder = new Context.ContextBuilder();
        Context context = builder.setConfiguration(configuration)
                .setLoggerFactory(loggerFactory)
                .setDbServiceFactory(dbServiceFactory)
                .setFileService(fileService)
                .build();

        String password = RandomStringUtils.randomAlphanumeric(10);
        String username = "admin";
        DBService adminDB = context.getDbServiceFactory().ofCollection("adminUser");
        adminDB.insert(new UserDetails(username, "Admin User", UserType.SUPER_ADMIN,
                BlinkUtils.sha256(password), "kpiyumal90@gmail.com"));
        logger.info("A user with username={} is created with a password={}", username, password);
        logger.info("This is a temporary password. Please change it as soon as you log in");
    }
}
