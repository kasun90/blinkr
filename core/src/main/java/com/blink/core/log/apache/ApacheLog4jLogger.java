package com.blink.core.log.apache;

import com.blink.core.log.Logger;
import org.apache.logging.log4j.LogManager;

public class ApacheLog4jLogger implements Logger {

    private org.apache.logging.log4j.Logger logger;

    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        org.apache.logging.log4j.core.config.ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());
    }

    public ApacheLog4jLogger() {
        logger = LogManager.getLogger("default");
    }

    public ApacheLog4jLogger(String name) {
        logger = LogManager.getLogger(name);
    }

    public ApacheLog4jLogger(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void debug(Object o) {
        logger.debug(o);
    }

    @Override
    public void debug(String format, Object... args) {
        logger.debug(format, args);
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void info(Object o) {
        logger.info(o);
    }

    @Override
    public void info(String format, Object... args) {
        logger.info(format, args);
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void error(Object o) {
        logger.error(o);
    }

    @Override
    public void error(String format, Object... args) {
        logger.error(format, args);
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    @Override
    public void warn(Object o) {
        logger.warn(o);
    }

    @Override
    public void warn(String format, Object... args) {
        logger.warn(format, args);
    }

    @Override
    public void exception(String s, Throwable throwable) {
        logger.fatal(s, throwable);
    }
}
