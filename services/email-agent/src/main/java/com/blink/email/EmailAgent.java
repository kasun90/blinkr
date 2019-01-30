package com.blink.email;

import com.blink.common.BlinkService;
import com.blink.core.messaging.Message;
import com.blink.core.messaging.Receiver;
import com.blink.core.service.Context;
import com.blink.email.helper.*;
import com.blink.email.helper.mailgun.MailgunEmailHelperFactory;
import com.blink.shared.email.EmailQueueMessage;
import com.blink.shared.email.EmailType;

import java.util.Map;

public class EmailAgent extends BlinkService implements Receiver {

    private EmailTemplateDataProvider provider;
    private EmailTemplateResolver resolver;
    private EmailHelper helper;

    public EmailAgent(Context context) throws Exception {
        super(context);
        this.provider = new FileTemplateDataProvider(context);
        this.resolver = new FileTemplateResolver();
        EmailHelperFactory factory = new MailgunEmailHelperFactory(context);
        this.helper = factory.create();
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
            info("Email queue message: {}", queueMessage);
            sendSingleMessage(queueMessage.getType(), queueMessage.getData(), queueMessage.getSubject(),
                    queueMessage.getRecipient());
        }

        message.acknowledge();
    }

    private void sendSingleMessage(EmailType type, Map<String, String> data, String subject, String to) throws Exception {
        provider.setEmail(to);
        String body = resolver.getBody(type, provider.with(data));
        provider.reset();
        helper.send(body, subject, to);
        info("Email sent [type={} to={}]", type.name(), to);
    }
}
