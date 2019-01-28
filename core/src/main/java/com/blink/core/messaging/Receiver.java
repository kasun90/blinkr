package com.blink.core.messaging;

public interface Receiver {
    void onMessage(Message message) throws Exception;
}
