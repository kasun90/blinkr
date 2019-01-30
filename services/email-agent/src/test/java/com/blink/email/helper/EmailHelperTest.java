package com.blink.email.helper;

import com.blink.core.log.LoggerFactory;
import com.blink.core.log.apache.ApacheLog4jLoggerFactory;
import com.blink.email.helper.mailgun.MailgunEmailHelperFactory;
import com.blink.shared.email.EmailType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

public class EmailHelperTest {

    EmailHelperFactory factory;

    @Before
    public void init() {
        Properties properties = new Properties();
        LoggerFactory loggerFactory = new ApacheLog4jLoggerFactory();
        properties.put("mail.smtps.host","smtp.mailgun.org");
        properties.put("mail.smtp.user", "default");
        properties.put("mail.smtp.password", "default");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtps.auth","true");
        properties.put("mail.default", "hello@justblink.xyz");
        factory = new MailgunEmailHelperFactory(properties, loggerFactory.getLogger("TEST-LOGGER"));
    }

    @Test
    public void sendEmail() throws Exception {
        EmailHelper emailHelper = factory.create();
        emailHelper.send("Hello 2", "Sample 2", "kpiyumal90@gmail.com");
        emailHelper.send("Hello 3", "Sample 3", "kpiyumal90@gmail.com");
    }

    @Test
    public void templateTest() throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("blink_logo", "http://localhost:5001/static/files/media/email/logo.svg");
        data.put("instagram_icon", "http://localhost:5001/static/files/media/email/icons/instagram.svg");
        data.put("facebook_icon", "http://localhost:5001/static/files/media/email/icons/facebook.svg");
        data.put("name", "Kasun");
        EmailTemplateDataProvider provider = new FileTemplateDataProvider(data, "http://localhost:5000").with(null);
        provider.setEmail("kpiyumal90@gmail.com");
        EmailTemplateResolver resolver = new FileTemplateResolver();
        System.out.println(resolver.getBody(EmailType.NEW_SUBSCRIBE, provider));
        EmailHelper emailHelper = factory.create();
        emailHelper.send(resolver.getBody(EmailType.NEW_SUBSCRIBE, provider), "New Sub Test", "kpiyumal90@gmail.com");
    }
}