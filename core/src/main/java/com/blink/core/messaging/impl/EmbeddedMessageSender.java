package com.blink.core.messaging.impl;

import com.blink.core.messaging.Sender;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class EmbeddedMessageSender implements Sender {

    private Session session;
    private MessageProducer producer;

    public EmbeddedMessageSender(ConnectionFactory connectionFactory, String channel) throws Exception {
        session = connectionFactory.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(new ActiveMQQueue(channel));
    }

    @Override
    public void send(Object message) throws Exception {
        producer.send(session.createTextMessage(message.toString()));
    }
}
