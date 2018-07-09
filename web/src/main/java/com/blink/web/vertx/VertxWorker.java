package com.blink.web.vertx;

import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import com.blink.core.system.ObjectCodec;
import com.blink.shared.system.InvalidRequest;
import com.blink.shared.system.WebInMessage;
import com.blink.shared.system.WebOutMessage;
import com.blink.utilities.BlinkJSON;
import com.blink.web.ClassTranslator;
import com.google.common.eventbus.Subscribe;
import io.vertx.core.http.HttpServerResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class VertxWorker {

    private Context context;
    private Map<String, HttpServerResponse> requestMap = new ConcurrentHashMap<>();
    private ClassTranslator translator = new ClassTranslator();
    private Logger logger;

    VertxWorker(Context context) {
        this.context = context;
        this.logger = context.getLoggerFactory().getLogger();
        context.getBus().register(this);
    }

    void publishRequest(String target, String message, HttpServerResponse response) {
        String reqID = UUID.randomUUID().toString();
        requestMap.put(reqID, response);

        if (target == null || target.isEmpty() || message == null || message.isEmpty()) {
            onWebOut(new WebOutMessage(reqID, new InvalidRequest("Invalid parameters")));
            return;
        }

        try {
            context.getBus().post(new WebInMessage(reqID, target, translator.fromPayload(message)));
        } catch (Exception e) {
            logger.exception("Web Request Exception", e);
            onWebOut(new WebOutMessage(reqID, new InvalidRequest(e.getMessage())));
        }
    }

    void respondToOption(HttpServerResponse response) {
        modifyHeaders(response);
        response.end();
    }

    @Subscribe
    public void onWebOut(WebOutMessage message) {
        try {
            HttpServerResponse response = requestMap.remove(message.getRequestID());

            if (response == null) {
                logger.error("Request ID {} not found to reply", message.getRequestID());
                return;
            }

            modifyHeaders(response);
            response.end(translator.toPayload(message.getData()));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void modifyHeaders(HttpServerResponse response) {
        response.putHeader("Access-Control-Allow-Origin", context.getConfiguration().getValue("allowedOrigin","*"));
        response.putHeader("Vary", "Origin");
        response.putHeader("Access-Control-Allow-Headers", "X-App-Key");
    }
}
