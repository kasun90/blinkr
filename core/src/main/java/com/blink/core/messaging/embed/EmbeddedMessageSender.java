package com.blink.core.messaging.embed;

import com.blink.core.messaging.Sender;
import com.blink.core.system.ObjectCodec;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class EmbeddedMessageSender implements Sender {

    private Session session;
    private MessageProducer producer;
    private ObjectCodec codec;

    public EmbeddedMessageSender(ConnectionFactory connectionFactory, String channel, ObjectCodec codec) throws Exception {
        session = connectionFactory.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(new ActiveMQQueue(channel));
        this.codec = codec;
    }

    @Override
    public void send(Object message) throws Exception {
        producer.send(session.createTextMessage(codec.toPayload(message)));
    }
}
