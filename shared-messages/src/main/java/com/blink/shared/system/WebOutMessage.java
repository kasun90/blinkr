package com.blink.shared.system;

import com.blink.utilities.BlinkJSON;

public class WebOutMessage {
    private String requestID;
    private Object data;

    public WebOutMessage(String requestID, Object data) {
        this.requestID = requestID;
        this.data = data;
    }

    public String getRequestID() {
        return requestID;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return BlinkJSON.toPrettyJSON(this);
    }
}
