package com.blink.web;

import com.blink.core.service.Context;

public abstract class WebServer {
    protected Context context;

    public WebServer(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void initialize() throws Exception;

    public abstract void start() throws Exception;
}
