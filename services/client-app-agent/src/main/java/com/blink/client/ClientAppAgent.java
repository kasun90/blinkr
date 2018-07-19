package com.blink.client;

import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.client.ClientRequestMessage;
import com.blink.shared.client.GenericReplyMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;

public class ClientAppAgent extends BaseService {

    DBService userMessageDB;

    public ClientAppAgent(Context context) {
        super(context);
        userMessageDB = context.getDbServiceFactory().ofCollection("userMessage");
    }

    @Subscribe
    public void onClientRequest(ClientRequestMessage requestMessage) throws Exception {
        Object enclosedMessage = requestMessage.getEnclosedMessage();
        String requestID = requestMessage.getRequestID();

        if (enclosedMessage instanceof UserMessage) {
            onUserMessage(requestID, UserMessage.class.cast(enclosedMessage));
        }
    }

    private void onUserMessage(String reqID, UserMessage message) throws Exception {
        message.setTimestamp(BlinkTime.getCurrentTimeMillis());
        userMessageDB.insertOrUpdate(new SimpleDBObject().append("timestamp", message.getTimestamp()),
                message);
        sendReply(reqID, new GenericReplyMessage("Message recorded"));
    }

    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
