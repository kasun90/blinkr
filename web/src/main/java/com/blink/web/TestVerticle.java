package com.blink.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TestVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Router router = Router.router(vertx);



        router.get("/hello").handler(new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {
                HttpServerResponse response = routingContext.response();


                response.putHeader("content-type", "text/html")
                        .end("ANee");
            }
        });


        vertx.createHttpServer().requestHandler(router::accept).listen(8100);


    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}
