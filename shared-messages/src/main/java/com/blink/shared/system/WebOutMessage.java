package com.blink.shared.system;

public class WebOutMessage {
    private String requestID;
    private String payload;

    public WebOutMessage(String requestID, String payload) {
        this.requestID = requestID;
        this.payload = payload;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getPayload() {
        return payload;
    }
}
