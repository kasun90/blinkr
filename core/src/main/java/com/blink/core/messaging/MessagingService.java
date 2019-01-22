package com.blink.core.messaging;

public interface MessagingService {
    Sender createSender(String channel) throws Exception;
    void createReceiver(String channel, Receiver receiver) throws Exception;
}
