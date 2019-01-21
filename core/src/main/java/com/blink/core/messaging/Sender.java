package com.blink.core.messaging;

public interface Sender {
    void send(Object message) throws Exception;
}
