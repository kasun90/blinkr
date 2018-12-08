package com.blink.shared.system;

public class WebInMessage {
    private String requestID;
    private String target;
    private String targetUser;
    private String appKey;
    private String appSession;
    private String remoteAddress;
    private Object data;

    public WebInMessage(String requestID, String target, String targetUser, String appKey, String appSession, String remoteAddress, Object data) {
        this.requestID = requestID;
        this.target = target;
        this.targetUser = targetUser;
        this.appKey = appKey;
        this.appSession = appSession;
        this.remoteAddress = remoteAddress;
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

    public String getTargetUser() {
        return targetUser;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSession() {
        return appSession;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }
}
