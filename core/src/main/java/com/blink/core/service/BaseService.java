package com.blink.core.service;

import com.blink.core.log.Logger;
import com.blink.core.setting.SettingReader;

public abstract class BaseService implements Logger {
    private static ThreadLocal<String> currentRequestID = new ThreadLocal<>();
    private Context context;
    private Logger logger;
    private SettingReader settingReader;
    public BaseService(Context context) {
        this.context = context;
        this.logger = context.getLoggerFactory().getLogger(getServiceName());
        this.settingReader = context.getDerivedService(SettingReader.class);
    }

    public abstract String getServiceName();

    protected String getSetting(String key) throws Exception {
        return settingReader.getSetting(key);
    }

    protected String getSetting(String key, String defaultValue) throws Exception {
        return settingReader.getSetting(key, defaultValue);
    }

    protected String getRequestID() {
        return currentRequestID.get();
    }

    protected void setRequestID(String requestID) {
        currentRequestID.set(requestID);
    }

    public void sendReply(Object message) {
        sendReply(currentRequestID.get(), message);
    }

    public abstract void sendReply(String requestID, Object message);

    public Context getContext() {
        return context;
    }

    @Override
    public void debug(Object o) {
        logger.debug(o);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void debug(String format, Object... args) {
        logger.debug(format, args);
    }

    @Override
    public void info(Object o) {
        logger.info(o);
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void info(String format, Object... args) {
        logger.info(format, args);
    }

    @Override
    public void warn(Object o) {
        logger.warn(o);
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    @Override
    public void warn(String format, Object... args) {
        logger.warn(format, args);
    }

    @Override
    public void error(Object o) {
        logger.error(o);
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void error(String format, Object... args) {
        logger.error(format, args);
    }

    @Override
    public void exception(String s, Throwable throwable) {
        logger.exception(s, throwable);
    }
}
