package com.blink.email.helper.mailgun;

import com.blink.email.helper.EmailHelper;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailgunEmailHelper implements EmailHelper {
    private Session session;
    private Properties properties;
    private Transport transport;

    public MailgunEmailHelper(Properties properties) throws Exception {
        this.properties = properties;
        this.session = Session.getInstance(properties);
        transport = session.getTransport("smtp");
        transport.connect(properties.getProperty("mail.smtps.host"), properties.getProperty("mail.smtp.user"),
                properties.getProperty("mail.smtp.password"));

    }

    @Override
    public void send(String body, String subject, String to) throws Exception {
        send(body, subject, properties.getProperty("mail.default"), to);
    }

    @Override
    public void send(String body, String subject, String from, String to) throws Exception {
        System.out.println("sending");
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");
        multipart.addBodyPart(mimeBodyPart);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(multipart);
        transport.sendMessage(message, message.getAllRecipients());

    }

    @Override
    public void close() throws Exception {
        if (transport != null)
            transport.close();
    }
}
