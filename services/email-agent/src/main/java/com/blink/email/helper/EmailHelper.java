package com.blink.email.helper;

import java.io.Closeable;

public interface EmailHelper extends AutoCloseable {
    void send(String body, String subject, String to) throws Exception;
    void send(String body, String subject, String from, String to) throws Exception;
}
