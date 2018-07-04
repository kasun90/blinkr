package com.blink.core.transport;


public interface Bus {
    void register(Object object);
    void post(Object object);
    void unregister(Object object);
}
