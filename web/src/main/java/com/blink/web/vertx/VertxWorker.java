package com.blink.web.vertx;

import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import com.blink.shared.system.InvalidRequest;
import com.blink.shared.system.WebInMessage;
import com.blink.shared.system.WebOutMessage;
import com.blink.web.ClassTranslator;
import com.google.common.eventbus.Subscribe;
import io.vertx.core.http.HttpServerResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class VertxWorker {

    private Context context;
    private Map<String, ServerResponse> requestMap = new ConcurrentHashMap<>();
    private ClassTranslator translator = new ClassTranslator();
    private Logger logger;

    VertxWorker(Context context) {
        this.context = context;
        this.logger = context.getLoggerFactory().getLogger("VertxWorker");
        context.getBus().register(this);
    }

    void publishRequest(String target, String targetUser, String appKey, String appSession, String message, String origin, String remoteAddress, HttpServerResponse response) {
        String reqID = UUID.randomUUID().toString();
        requestMap.put(reqID, new ServerResponse(origin, response));

        if (target == null || target.isEmpty() || message == null || message.isEmpty()) {
            onWebOut(new WebOutMessage(reqID, new InvalidRequest("Invalid parameters")));
            return;
        }

        try {
            context.getBus().post(new WebInMessage(reqID, target, targetUser, appKey, appSession, remoteAddress, translator.fromPayload(message)));
        } catch (Exception e) {
            logger.exception("Web Request Exception", e);
            onWebOut(new WebOutMessage(reqID, new InvalidRequest(e.getMessage())));
        }
    }

    void respondToOption(String origin, HttpServerResponse response) {
        modifyHeaders(origin, response);
        response.end();
    }

    void respondToInvalidOrigin(HttpServerResponse response) {
        response.setStatusCode(403);
        response.end();
    }

    @Subscribe
    public void onWebOut(WebOutMessage message) {
        try {
            ServerResponse serverResponse = requestMap.get(message.getRequestID());

            if (serverResponse == null) {
                logger.error("Request ID {} not found to reply", message.getRequestID());
                return;
            }

            modifyHeaders(serverResponse.getOrigin(), serverResponse.getResponse());
            serverResponse.getResponse().end(translator.toPayload(message.getData()));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void modifyHeaders(String origin, HttpServerResponse response) {
        if (origin != null) {
            response.putHeader("Access-Control-Allow-Origin", origin);
            response.putHeader("Vary", "Origin");
        }
        response.putHeader("Access-Control-Allow-Headers", "X-App-Key,X-App-Session");
    }
}
