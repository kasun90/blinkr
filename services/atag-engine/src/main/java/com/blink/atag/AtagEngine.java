package com.blink.atag;

public interface AtagEngine {
    void initialize() throws Exception;
    void process(String raw) throws Exception;

}
