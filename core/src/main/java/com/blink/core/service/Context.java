package com.blink.core.service;

import com.blink.core.database.DBServiceFactory;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.file.FileService;
import com.blink.core.log.LoggerFactory;
import com.blink.core.setting.SettingReader;
import com.blink.core.transport.Bus;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Context {
    private Configuration configuration;
    private Bus bus;
    private LoggerFactory loggerFactory;
    private DBServiceFactory dbServiceFactory;
    private FileService fileService;
    private Map<String, DerivedService> derivedServiceMap;

    private Context(ContextBuilder builder) {
        this.configuration = builder.configuration;
        this.bus = builder.bus;
        this.loggerFactory = builder.loggerFactory;
        this.dbServiceFactory = builder.dbServiceFactory;
        this.fileService = builder.fileService;
        derivedServiceMap = new HashMap<>();
    }

    public Bus getBus() {
        return bus;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public DBServiceFactory getDbServiceFactory() {
        return dbServiceFactory;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void registerDerivedService(Class<? extends DerivedService> clazz, DerivedService service) {
        if (derivedServiceMap.containsKey(clazz.getName()))
            throw new BlinkRuntimeException(MessageFormat.format("Derived service already defined: {0}", clazz.getName()));
        else
            derivedServiceMap.put(clazz.getName(), service);
    }

    public <T extends DerivedService> T getDerivedService(Class<T> clazz) {
        DerivedService derivedService = derivedServiceMap.get(clazz.getName());
        return clazz.cast(derivedService);
    }

    public static class ContextBuilder {
        private Configuration configuration;
        private Bus bus;
        private LoggerFactory loggerFactory;
        private DBServiceFactory dbServiceFactory;
        private FileService fileService;

        public ContextBuilder setBus(Bus bus) {
            this.bus = bus;
            return this;
        }

        public ContextBuilder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ContextBuilder setLoggerFactory(LoggerFactory loggerFactory) {
            this.loggerFactory = loggerFactory;
            return this;
        }

        public ContextBuilder setDbServiceFactory(DBServiceFactory dbServiceFactory) {
            this.dbServiceFactory = dbServiceFactory;
            return this;
        }

        public ContextBuilder setFileService(FileService fileService) {
            this.fileService = fileService;
            return this;
        }

        public Context build() {
            return new Context(this);
        }
    }

}
