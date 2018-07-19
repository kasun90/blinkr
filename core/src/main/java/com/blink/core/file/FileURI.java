package com.blink.core.file;

public interface FileURI {
    FileURI appendResource(String resource);
    String build();
}
