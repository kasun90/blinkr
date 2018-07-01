package com.blink.web.vertx;

import com.blink.core.service.Context;
import com.blink.web.WebServer;
import io.vertx.core.Vertx;

public class VertxWebServer extends WebServer {


    public VertxWebServer(Context context) {
        super(context);

    }

    @Override
    public void initialize() throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BlinkVerticle(context));
    }

    @Override
    public void start() throws Exception {

    }
}
