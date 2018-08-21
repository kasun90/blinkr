package com.blink.web.meta.impl;

import com.blink.web.meta.MetaPath;
import com.blink.web.meta.MetaTagResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MetaTagResolverImpl implements MetaTagResolver {

    private Map<String, MetaPath> paths = new ConcurrentHashMap<>();

    @Override
    public MetaPath register(String path) {
        return paths.computeIfAbsent(path, MetaPathImpl::new);
    }

    @Override
    public String on(String path) {

        return null;
    }
}
