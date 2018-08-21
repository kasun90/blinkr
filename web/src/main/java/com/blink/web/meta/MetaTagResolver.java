package com.blink.web.meta;

public interface MetaTagResolver {
    MetaPath register(String path);
    String on(String path);
}
