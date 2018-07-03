package com.blink.core.service;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.Logger;

public final class Context {
    private Configuration configuration;
    private EventBus eventBus;
    private Logger logger;

    public Context(ContextBuilder builder) {
        this.configuration = builder.configuration;
        this.eventBus = builder.eventBus;
        this.logger = builder.logger;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Logger getLogger() {
        return logger;
    }

    public static class ContextBuilder {
        private Configuration configuration;
        private EventBus eventBus;
        private Logger logger;

        public ContextBuilder setEventBus(EventBus eventBus) {
            this.eventBus = eventBus;
            return this;
        }

        public ContextBuilder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ContextBuilder setLogger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public Context build() {
            return new Context(this);
        }
    }

}
