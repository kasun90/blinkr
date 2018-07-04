package com.blink.core.log;

public interface LoggerFactory {
    Logger getLogger();
    Logger getLogger(String name);
    Logger getLogger(Class<?> clazz);
}
