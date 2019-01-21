package com.blink.core.messaging.impl;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.messaging.MessagingService;
import com.blink.core.messaging.Receiver;
import com.blink.core.messaging.Sender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

public class EmbeddedMessageService implements MessagingService {

    private final String url = "tcp://localhost:61616";
    private ConnectionFactory connectionFactory;

    public EmbeddedMessageService() throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector(url);
        broker.start();
        connectionFactory = new ActiveMQConnectionFactory(url);
    }

    @Override
    public Sender createSender(String channel) throws Exception {
        return new EmbeddedMessageSender(connectionFactory, channel);
    }

    @Override
    public void createReceiver(String channel, Receiver receiver) throws Exception {
        Session session = connectionFactory.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createConsumer(new ActiveMQQueue(channel)).setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage content = (TextMessage) message;
                try {
                    receiver.onMessage(content.getText());
                } catch (Exception e) {
                    throw new BlinkRuntimeException(e);
                }
            }
        });
    }
}
