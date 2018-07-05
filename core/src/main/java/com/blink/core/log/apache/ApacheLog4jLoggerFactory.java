package com.blink.core.log.apache;

import com.blink.core.log.Logger;
import com.blink.core.log.LoggerFactory;

public class ApacheLog4jLoggerFactory implements LoggerFactory {

    @Override
    public Logger getLogger() {
        return new ApacheLog4jLogger();
    }

    @Override
    public Logger getLogger(String name) {
        return new ApacheLog4jLogger(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new ApacheLog4jLogger(clazz);
    }
}
