package com.blink.core.service;

import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;
import com.blink.core.transport.Bus;

public final class Context {
    private Configuration configuration;
    private Bus bus;
    private LoggerFactory loggerFactory;

    public Context(ContextBuilder builder) {
        this.configuration = builder.configuration;
        this.bus = builder.bus;
        this.loggerFactory = builder.loggerFactory;
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

    public static class ContextBuilder {
        private Configuration configuration;
        private Bus bus;
        private LoggerFactory loggerFactory;

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

        public Context build() {
            return new Context(this);
        }
    }

}
