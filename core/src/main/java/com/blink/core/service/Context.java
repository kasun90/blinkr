package com.blink.core.service;

import com.blink.core.database.DBService;
import com.blink.core.file.FileService;
import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;
import com.blink.core.transport.Bus;

public final class Context {
    private Configuration configuration;
    private Bus bus;
    private LoggerFactory loggerFactory;
    private DBService dbService;
    private FileService fileService;

    public Context(ContextBuilder builder) {
        this.configuration = builder.configuration;
        this.bus = builder.bus;
        this.loggerFactory = builder.loggerFactory;
        this.dbService = builder.dbService;
        this.fileService = builder.fileService;
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

    public DBService getDbService() {
        return dbService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public static class ContextBuilder {
        private Configuration configuration;
        private Bus bus;
        private LoggerFactory loggerFactory;
        private DBService dbService;
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

        public ContextBuilder setDbService(DBService dbService) {
            this.dbService = dbService;
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
