package com.blink.web.vertx;

import com.blink.core.service.Context;
import com.blink.core.system.ObjectCodec;
import com.blink.shared.system.WebInMessage;
import com.blink.shared.system.WebOutMessage;
import com.google.common.eventbus.Subscribe;
import io.vertx.core.http.HttpServerResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class VertxWorker implements ObjectCodec {

    private Context context;
    private Map<String, HttpServerResponse> requestMap = new ConcurrentHashMap<>();

    VertxWorker(Context context) {
        this.context = context;
    }

    void publishRequest(String target, String message, HttpServerResponse response) {
        try {
            String reqID = UUID.randomUUID().toString();
            requestMap.put(reqID, response);
            context.getEventBus().post(new WebInMessage(reqID, target, fromPayload(message)));
        } catch (Exception e) {

        }
    }

    @Subscribe
    public void onReply(WebOutMessage message) {
        try {
            HttpServerResponse response = requestMap.remove(message.getRequestID());

            if (response == null) {
                context.getLogger().error("Request ID {} not found to reply", message.getRequestID());
                return;
            }

            response.end(toPayload(message.getData()));
        } catch (Exception e) {

        }
    }


    @Override
    public Object fromPayload(String payload) throws Exception {
        return null;
    }

    @Override
    public String toPayload(Object object) throws Exception {
        return null;
    }
}
