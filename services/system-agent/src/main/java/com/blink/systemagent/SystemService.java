package com.blink.systemagent;

import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.admin.AdminRequestMessage;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.system.*;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class SystemService extends BaseService {


    public SystemService(Context context) {
        super(context);
    }

    @Subscribe
    public void onWebIn(WebInMessage message) {
        String target = message.getTarget();

        Object data = message.getData();

        if (data instanceof WebRequestMessage)
            ((WebRequestMessage) data).setRequestID(message.getRequestID());

        switch (target) {
            case "CLIENT":
                onTargetClient(message.getRequestID(), message.getRemoteAddress(), data);
                break;
            case "ADMIN":
                onTargetAdmin(message.getRequestID(), message.getTargetUser(), message.getAppSession(), data);
                break;
            default:
                onReply(new ReplyMessage(message.getRequestID(), new InvalidRequest("Invalid target")));
                break;
        }
    }

    private void onTargetClient(String requestID, String remoteAddress, Object data) {
        getContext().getBus().post(new ClientRequestMessage(requestID, remoteAddress, data));
    }

    private void onTargetAdmin(String requestID, String targetUser, String sessionID, Object data) {
        getContext().getBus().post(new AdminRequestMessage(requestID, targetUser, sessionID, data));
    }

    @Subscribe
    void onReply(ReplyMessage replyMessage) {
        getContext().getBus().post(new WebOutMessage(replyMessage.getRequestID(), replyMessage.getData()));
    }

    @Subscribe
    void onDeadEvent(DeadEvent e) {
        error("Dead event received {}", e.getEvent());
    }


    @Override
    public String getServiceName() {
        return "SYSTEM";
    }
}
