package com.blink.core.service;

import com.blink.core.log.Logger;
import com.blink.shared.system.InvalidRequest;
import com.blink.shared.system.ReplyMessage;

public abstract class BaseService implements Logger {
    public abstract String getServiceName();

    private Context context;
    private Logger logger;

    public BaseService(Context context) {
        this.context = context;
        this.logger = context.getLoggerFactory().getLogger(getServiceName());
    }

    public void sendReply(String requestID, Object message) {
        context.getBus().post(new ReplyMessage(requestID, message));
    }

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
