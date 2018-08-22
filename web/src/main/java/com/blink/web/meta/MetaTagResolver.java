package com.blink.web.meta;

public interface MetaTagResolver {
    void init() throws Exception;
    MetaPath register(String path);
    String on(String path, String absoluteURI) throws Exception;
}
