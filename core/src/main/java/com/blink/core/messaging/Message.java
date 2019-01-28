package com.blink.core.messaging;

public interface Message {
    Object getContent() throws Exception;
    void acknowledge() throws Exception;
}
