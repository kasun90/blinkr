package com.blink.email;

import com.blink.common.BlinkService;
import com.blink.core.messaging.Message;
import com.blink.core.messaging.Receiver;
import com.blink.core.service.Context;
import com.blink.shared.email.EmailQueueMessage;

public class EmailAgent extends BlinkService implements Receiver {

    public EmailAgent(Context context) throws Exception {
        super(context);
        context.getMessagingService().createReceiver("email", this);
    }

    @Override
    public String getServiceName() {
        return "EMAIL";
    }

    @Override
    public void onMessage(Message message) throws Exception {
        Object content = message.getContent();

        if (content instanceof EmailQueueMessage) {
            EmailQueueMessage queueMessage = (EmailQueueMessage) content;

        }

        message.acknowledge();
    }
}
