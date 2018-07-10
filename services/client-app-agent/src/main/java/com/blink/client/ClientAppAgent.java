package com.blink.client;

import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.client.GenericReplyMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.google.common.eventbus.Subscribe;

public class ClientAppAgent extends BaseService {


    public ClientAppAgent(Context context) {
        super(context);
    }

    @Subscribe
    public void onClientRequest(ClientRequestMessage requestMessage) {
        Object enclosedMessage = requestMessage.getEnclosedMessage();
        String requestID = requestMessage.getRequestID();

        if (enclosedMessage instanceof UserMessage) {
            onUserMessage(requestID, UserMessage.class.cast(enclosedMessage));
        }
    }

    private void onUserMessage(String reqID, UserMessage message) {

        sendReply(reqID, new GenericReplyMessage("Message recorded"));
    }

    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
