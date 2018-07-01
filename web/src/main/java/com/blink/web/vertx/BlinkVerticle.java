package com.blink.web.vertx;

import com.blink.core.service.Context;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Map;

public class BlinkVerticle extends AbstractVerticle {

    private Context context;
    private VertxWorker worker;

    public BlinkVerticle(Context context) {
        this.context = context;
        this.worker = new VertxWorker(context);
        context.getEventBus().register(this.worker);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Router clientRouter = Router.router(vertx);

        clientRouter.route().handler(BodyHandler.create());
        clientRouter.post("/client").handler(routingContext -> {
            MultiMap params = routingContext.request().params();
            worker.publishRequest(params.get("name"), routingContext.response());
        });

        vertx.createHttpServer().requestHandler(clientRouter::accept).listen(context.getConfiguration().getClientPort(),
                result -> {
            if (result.succeeded())
                startFuture.complete();
            else
                startFuture.fail(result.cause());
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}
