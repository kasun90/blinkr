package com.blink.runner;

import com.blink.core.database.DBService;
import com.blink.core.database.mongodb.MongoDBService;
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

public class EnvironmentCreator {
    public static void main(String[] args) throws Exception {
        new EnvironmentCreator().create();
    }

    private void create() throws Exception {
        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        Logger logger = loggerFactory.getLogger("Environment");

        ConfigurationFactory.setFactory(new FileConfigurationFactory());
        Configuration configuration = ConfigurationFactory.getFactory().getConfiguration();

        DBService dbService = new MongoDBService(configuration);
        FileService fileService = new LocalFileService(configuration);

        Context.ContextBuilder builder = new Context.ContextBuilder();
        Context context = builder.setConfiguration(configuration)
                .setLoggerFactory(loggerFactory)
                .setDbService(dbService)
                .setFileService(fileService)
                .build();

        //create users
        //admin user with password 1234

        DBService adminDB = context.getDbService().withCollection("adminUser");
        adminDB.insert(new UserDetails("admin", "Kasun Piyumal", UserType.SUPER_ADMIN,
                "03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4", "kpiyumal90@gmail.com"));
        logger.info("Users created");
    }
}
