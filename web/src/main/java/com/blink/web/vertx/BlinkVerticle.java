package com.blink.web.vertx;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import com.blink.web.meta.MetaTagResolver;
import com.blink.web.meta.handlers.AlbumTagHandler;
import com.blink.web.meta.impl.MetaTagResolverImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.File;
import java.util.Set;

public class BlinkVerticle extends AbstractVerticle {

    private Context context;
    private VertxWorker worker;
    private Logger logger;
    private Set<String> allowedOrigins;
    private MetaTagResolver metaTagResolver;

    public BlinkVerticle(Context context) throws Exception {
        this.context = context;
        this.worker = new VertxWorker(context);
        this.logger = context.getLoggerFactory().getLogger("WEB");
        this.allowedOrigins = context.getConfiguration().getValues("allowedOrigins");
        this.metaTagResolver = new MetaTagResolverImpl(context);
        this.metaTagResolver.register("/albums/view/:key").handler(new AlbumTagHandler(context));
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Router clientRouter = Router.router(vertx);

        clientRouter.route("/client").handler(BodyHandler.create());
        clientRouter.post("/client").handler(this::routeAPIRequest);
        clientRouter.options("/client").handler(this::respondToOption);

        File staticFiles = new File(context.getConfiguration().getValue("staticFilesRoot"));
        staticFiles.mkdirs();
        final String staticFilesPath = staticFiles.getPath();
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/").append(context.getConfiguration().getValue("staticFilesRoot")).append("*");
        clientRouter.get(pathBuilder.toString())
                .handler(StaticHandler.create(staticFilesPath).setCachingEnabled(false).setMaxAgeSeconds(0).setFilesReadOnly(false));

        final String path = new File(context.getConfiguration().getValue("clientRoot")).getPath();
        final String faviconPath = new File(context.getConfiguration().getValue("clientRoot") + "/favicon.ico").getPath();

        clientRouter.get("/favicon.ico").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Type", "image/x-icon");
            response.sendFile(faviconPath);
        });

        //clientRouter.route().handler(StaticHandler.create(path).setCachingEnabled(false).setMaxAgeSeconds(1).setFilesReadOnly(false));

        clientRouter.get("/service-worker.js").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.sendFile(new File(context.getConfiguration().getValue("clientRoot") + "/service-worker.js").getPath());
        });

        clientRouter.get("/manifest.json").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.sendFile(new File(context.getConfiguration().getValue("clientRoot") + "/manifest.json").getPath());
        });

        clientRouter.get("/static/*").handler(StaticHandler.create(path + "/static").setCachingEnabled(false).setMaxAgeSeconds(1).setFilesReadOnly(false));
        clientRouter.get("/*").handler(routingContext -> {
            try {
                String response = metaTagResolver.on(routingContext.request().path(), routingContext.request().absoluteURI());
                routingContext.response().end(response);
            } catch (Exception e) {
                throw new BlinkRuntimeException(e);
            }
        });

        vertx.createHttpServer().requestHandler(clientRouter::accept).listen(context.getConfiguration().getClientPort());
        logger.info("Client server started on port {}", context.getConfiguration().getClientPort());

        //admin Router

        Router adminRouter = Router.router(vertx);

        adminRouter.route("/admin").handler(BodyHandler.create());
        adminRouter.post("/admin").handler(this::routeAPIRequest);
        adminRouter.options("/admin").handler(this::respondToOption);

        final String adminPath = new File(context.getConfiguration().getValue("adminRoot")).getPath();
        final String adminFaviconPath = new File(context.getConfiguration().getValue("adminRoot") + "/favicon.ico").getPath();

        adminRouter.get("/favicon.ico").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Type", "image/x-icon");
            response.sendFile(adminFaviconPath);
        });

        adminRouter.route("/*").handler(StaticHandler.create(adminPath).setCachingEnabled(false).setMaxAgeSeconds(1).setFilesReadOnly(false));

        vertx.createHttpServer().requestHandler(adminRouter::accept).listen(context.getConfiguration().getAdminPort());

        logger.info("Admin server started on port {}", context.getConfiguration().getAdminPort());
    }

    private void routeAPIRequest(RoutingContext context) {
        MultiMap params = context.request().params();
        MultiMap headers = context.request().headers();
        String origin = headers.get("Origin");
        String appKey = headers.get("X-App-Key");
        String appSession = headers.get("X-App-Session");
        if (allowedOrigins.isEmpty() || allowedOrigins.contains(origin))
            worker.publishRequest(params.get("target"), params.get("targetUser"), appKey, appSession, params.get("message"), origin, context.response());
        else {
            logger.error("Request from unauthorized origin [origin={}]", origin);
            worker.respondToInvalidOrigin(context.response());
        }
    }

    private void respondToOption(RoutingContext context) {
        MultiMap headers = context.request().headers();
        String origin = headers.get("Origin");
        worker.respondToOption(origin, context.response());
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}
