package com.blink.web.vertx;

import com.blink.core.service.Context;
import com.blink.shared.system.WebInMessage;
import com.blink.shared.system.WebOutMessage;
import com.google.common.eventbus.Subscribe;
import io.vertx.core.http.HttpServerResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class VertxWorker {

    private Context context;
    private Map<String, HttpServerResponse> requestMap = new ConcurrentHashMap<>();

    VertxWorker(Context context) {
        this.context = context;
    }

    void publishRequest(String body, HttpServerResponse response) {
        String reqID = UUID.randomUUID().toString();
        requestMap.put(reqID, response);
        context.getEventBus().post(new WebInMessage(reqID, body));
    }

    @Subscribe
    public void onReply(WebOutMessage message) {
        HttpServerResponse response = requestMap.remove(message.getRequestID());

        if (response == null) {
            System.out.println("No Request ID");
            return;
        }

        response.end(message.getPayload());
    }


}
