package com.blink.core.messaging.embed;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.messaging.Message;
import com.blink.core.messaging.MessagingService;
import com.blink.core.messaging.Receiver;
import com.blink.core.messaging.Sender;
import com.blink.core.system.ObjectCodec;
import io.hawt.embedded.Main;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

public class EmbeddedMessageService implements MessagingService {
    private ConnectionFactory connectionFactory;
    private ObjectCodec codec;

    public EmbeddedMessageService(ObjectCodec codec, String hawtioWar) throws Exception {
        final String url = "tcp://localhost:61616";
        BrokerService broker = new BrokerService();
        broker.addConnector(url);
        broker.start();
        System.setProperty("hawtio.authenticationEnabled", "false");
        Main main = new Main();
        main.setWar(hawtioWar);
        main.setPort(5002);
        main.setContextPath("/");
        main.run();
        this.connectionFactory = new ActiveMQConnectionFactory(url);
        this.codec = codec;
    }

    @Override
    public Sender createSender(String channel) throws Exception {
        return new EmbeddedMessageSender(connectionFactory, channel, codec);
    }

    @Override
    public void createReceiver(String channel, Receiver receiver) throws Exception {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        session.createConsumer(new ActiveMQQueue(channel)).setMessageListener(message -> {
            if (message instanceof TextMessage) {
                final TextMessage content = (TextMessage) message;
                try {
                    final Message wrapper = new Message() {
                        @Override
                        public Object getContent() throws Exception {
                            return codec.fromPayload(content.getText());
                        }

                        @Override
                        public void acknowledge() throws Exception {
                            content.acknowledge();
                        }
                    };
                    receiver.onMessage(wrapper);
                } catch (Exception e) {
                    throw new BlinkRuntimeException(e);
                }
            }
        });
        connection.start();
    }
}
