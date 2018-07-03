package com.blink.core.system;

public interface ObjectCodec {
    Object fromPayload(String payload) throws Exception;
    String toPayload(Object object) throws Exception;
}
