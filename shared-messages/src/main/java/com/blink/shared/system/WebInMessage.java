package com.blink.shared.system;

public class WebInMessage {
    private String requestID;
    private String target;
    private Object data;

    public WebInMessage(String requestID, String target, Object data) {
        this.requestID = requestID;
        this.target = target;
        this.data = data;
    }

    public String getRequestID() {
        return requestID;
    }

    public Object getData() {
        return data;
    }

    public String getTarget() {
        return target;
    }
}
