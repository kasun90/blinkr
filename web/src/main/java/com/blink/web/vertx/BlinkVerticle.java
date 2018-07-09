package com.blink.web.vertx;

import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BlinkVerticle extends AbstractVerticle {

    private Context context;
    private VertxWorker worker;
    private Logger logger;

    public BlinkVerticle(Context context) {
        this.context = context;
        this.worker = new VertxWorker(context);
        this.logger = context.getLoggerFactory().getLogger("WEB");
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Router clientRouter = Router.router(vertx);

        clientRouter.route().handler(BodyHandler.create());
        clientRouter.post("/client").handler(routingContext -> {
            MultiMap params = routingContext.request().params();
            MultiMap headers = routingContext.request().headers();
            worker.publishRequest(params.get("target"), params.get("message"), routingContext.response());
        });

        clientRouter.options("/client").handler(routingContext -> {
             worker.respondToOption(routingContext.response());
        });



        vertx.createHttpServer().requestHandler(clientRouter::accept).listen(context.getConfiguration().getClientPort(),
                result -> {
                    if (result.succeeded())
                        startFuture.complete();
                    else
                        startFuture.fail(result.cause());
                });
        logger.info("Server started on port {}", context.getConfiguration().getClientPort());

    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}
