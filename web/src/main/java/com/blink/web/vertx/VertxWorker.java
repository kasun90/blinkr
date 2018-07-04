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
    }

    void publishRequest(String target, String message, HttpServerResponse response) {
        String reqID = UUID.randomUUID().toString();
        requestMap.put(reqID, response);
        try {
            context.getBus().post(new WebInMessage(reqID, target, translator.fromPayload(message)));
        } catch (Exception e) {
            onReply(new WebOutMessage(reqID, new InvalidRequest(e.getMessage())));
        }
    }

    @Subscribe
    public void onReply(WebOutMessage message) {
        try {
            HttpServerResponse response = requestMap.remove(message.getRequestID());

            if (response == null) {
                logger.error("Request ID {} not found to reply", message.getRequestID());
                return;
            }

            response.end(translator.toPayload(message.getData()));
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
