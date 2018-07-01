package com.blink.core.service;

import com.google.common.eventbus.EventBus;

public final class Context {
    private Configuration configuration;
    private EventBus eventBus;

    public Context(ContextBuilder builder) {
        this.configuration = builder.configuration;
        this.eventBus = builder.eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Configuration getConfiguration() {
        return configuration;
    }


    public static class ContextBuilder {
        private Configuration configuration;
        private EventBus eventBus;

        public ContextBuilder setEventBus(EventBus eventBus) {
            this.eventBus = eventBus;
            return this;
        }


        public ContextBuilder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Context build() {
            return new Context(this);
        }
    }

}
