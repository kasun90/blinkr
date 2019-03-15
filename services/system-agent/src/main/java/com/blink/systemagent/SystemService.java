package com.blink.systemagent;

import com.blink.common.BlinkService;
import com.blink.core.service.Context;
import com.blink.shared.admin.AdminRequestMessage;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.system.*;
import xyz.justblink.eventbus.DeadEvent;
import xyz.justblink.eventbus.Subscribe;

public class SystemService extends BlinkService {


    public SystemService(Context context) throws Exception {
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
        getContext().getBusService().getDefault().post(new ClientRequestMessage(requestID, remoteAddress, data));
    }

    private void onTargetAdmin(String requestID, String targetUser, String sessionID, Object data) {
        getContext().getBusService().getDefault().post(new AdminRequestMessage(requestID, targetUser, sessionID, data));
    }

    @Subscribe
    void onReply(ReplyMessage replyMessage) {
        getContext().getBusService().getDefault().post(new WebOutMessage(replyMessage.getRequestID(), replyMessage.getData()));
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
