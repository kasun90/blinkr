package com.blink.core.service;

import com.blink.core.log.Logger;

public abstract class BaseService implements Logger {
    public abstract String getServiceName();

    private Context context;

    public BaseService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void debug(Object o) {
        context.getLogger().debug(o);
    }

    @Override
    public void debug(String s) {
        context.getLogger().debug(s);
    }

    @Override
    public void debug(String format, Object... args) {
        context.getLogger().debug(format, args);
    }

    @Override
    public void info(Object o) {
        context.getLogger().info(o);
    }

    @Override
    public void info(String s) {
        context.getLogger().info(s);
    }

    @Override
    public void info(String format, Object... args) {
        context.getLogger().info(format, args);
    }

    @Override
    public void warn(Object o) {
        context.getLogger().warn(o);
    }

    @Override
    public void warn(String s) {
        context.getLogger().warn(s);
    }

    @Override
    public void warn(String format, Object... args) {
        context.getLogger().warn(format, args);
    }

    @Override
    public void error(Object o) {
        context.getLogger().error(o);
    }

    @Override
    public void error(String s) {
        context.getLogger().error(s);
    }

    @Override
    public void error(String format, Object... args) {
        context.getLogger().error(format, args);
    }

    @Override
    public void exception(String s, Throwable throwable) {
        context.getLogger().fatal(s, throwable);
    }
}
