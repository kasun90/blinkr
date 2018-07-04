package com.blink.core.log;

public interface Logger {
    void debug(String s);
    void debug(Object o);
    void debug(String format, Object... args);
    void info(String s);
    void info(Object o);
    void info(String format, Object... args);
    void error(String s);
    void error(Object o);
    void error(String format, Object... args);
    void warn(String s);
    void warn(Object o);
    void warn(String format, Object... args);
    void exception(String s, Throwable throwable);
}
