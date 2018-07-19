package com.blink.core.file.local;

import com.blink.core.file.FileURI;

public class LocalFileURI implements FileURI {

    private StringBuilder builder;

    public LocalFileURI() {
        builder = new StringBuilder();
    }

    public LocalFileURI(String base) {
        builder = new StringBuilder(base);
    }

    @Override
    public FileURI appendResource(String resource) {
        if (!resource.startsWith("/") && builder.length() != 0)
            builder.append("/");
        builder.append(resource);
        return this;
    }

    @Override
    public String build() {
        String path = builder.toString();
        builder.setLength(0);
        return path;
    }
}
