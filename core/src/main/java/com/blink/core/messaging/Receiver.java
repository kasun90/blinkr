package com.blink.core.messaging;

public interface Receiver {
    void onMessage(Object message) throws Exception;
}
