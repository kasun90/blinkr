package com.blink.email.helper.mailgun;

import com.blink.core.log.Logger;
import com.blink.email.helper.EmailHelper;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailgunEmailHelper implements EmailHelper, ConnectionListener {
    private Session session;
    private Properties properties;
    private Transport transport;
    private Logger logger;

    MailgunEmailHelper(Properties properties, Logger logger) throws Exception {
        this.logger = logger;
        this.properties = properties;
        this.session = Session.getInstance(properties);
        createAndConnect();
        logger.info("Mail helper is ready");
    }

    private void createAndConnect() throws Exception {
        transport = session.getTransport("smtp");
        transport.addConnectionListener(this);
        connect();
    }

    private void connect() throws Exception {
        transport.connect(properties.getProperty("mail.smtps.host"), properties.getProperty("mail.smtp.user"),
                properties.getProperty("mail.smtp.password"));
    }

    private void closeConnection() throws Exception {
        if (transport != null) {
            transport.close();
            transport.removeConnectionListener(this);
            transport = null;
        }
    }

    @Override
    public void opened(ConnectionEvent connectionEvent) {

    }

    @Override
    public void disconnected(ConnectionEvent connectionEvent) {
        logger.warn("SMTP connection disconnected");
    }

    @Override
    public void closed(ConnectionEvent connectionEvent) {
        logger.warn("SMTP connection closed");
    }

    @Override
    public void send(String body, String subject, String to) throws Exception {
        send(body, subject, properties.getProperty("mail.default"), to);
    }

    @Override
    public void send(String body, String subject, String from, String to) throws Exception {
        if (!transport.isConnected()) {
            logger.warn("Transport is not connected. Trying to reconnect");
            connect();
        }

        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");
        multipart.addBodyPart(mimeBodyPart);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from, "Blink"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(multipart);
        transport.sendMessage(message, message.getAllRecipients());
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }
}
