package com.blink.web.vertx;

import io.vertx.core.http.HttpServerResponse;

public final class ServerResponse {
    private String origin;
    private HttpServerResponse response;

    public ServerResponse(String origin, HttpServerResponse response) {
        this.origin = origin;
        this.response = response;
    }

    public String getOrigin() {
        return origin;
    }

    public HttpServerResponse getResponse() {
        return response;
    }
}
