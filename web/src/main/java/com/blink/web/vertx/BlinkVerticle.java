package com.blink.web.vertx;

import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.File;

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

        clientRouter.route("/client").handler(BodyHandler.create());
        clientRouter.post("/client").handler(routingContext -> {
            MultiMap params = routingContext.request().params();
            MultiMap headers = routingContext.request().headers();
            worker.publishRequest(params.get("target"), params.get("message"), routingContext.response());
        });

        clientRouter.options("/client").handler(routingContext -> {
            worker.respondToOption(routingContext.response());
        });

        final String path = new File(context.getConfiguration().getValue("clientRoot")).getPath();
        final String faviconPath = new File(context.getConfiguration().getValue("clientRoot") + "/favicon.ico").getPath();

        clientRouter.get("/favicon.ico").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Type", "image/x-icon");
            response.sendFile(faviconPath);
        });

        clientRouter.route().handler(StaticHandler.create(path).setCachingEnabled(false).setMaxAgeSeconds(1).setFilesReadOnly(false));

        File staticFiles = new File(context.getConfiguration().getValue("staticFilesRoot"));
        staticFiles.mkdirs();
        final String staticFilesPath = staticFiles.getPath();
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/").append(context.getConfiguration().getValue("staticFilesRoot")).append("*");
        clientRouter.get(pathBuilder.toString())
                .handler(StaticHandler.create(staticFilesPath).setCachingEnabled(false).setMaxAgeSeconds(0).setFilesReadOnly(false));

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
