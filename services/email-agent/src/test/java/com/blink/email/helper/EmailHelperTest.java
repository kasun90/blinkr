package com.blink.email.helper;

import com.blink.email.helper.mailgun.MailgunEmailHelperFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class EmailHelperTest {

    EmailHelperFactory factory;

    @Before
    public void init() {
        Properties properties = new Properties();
        properties.put("mail.smtps.host","smtp.mailgun.org");
        properties.put("mail.smtp.user", "postmaster@mail.justblink.xyz");
        properties.put("mail.smtp.password", "c96696c0a7b0672084293a2ac7f9bf25-060550c6-81f56f25");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtps.auth","true");
        properties.put("mail.default", "hello@justblink.xyz");
        factory = new MailgunEmailHelperFactory(properties);
    }

    @Test
    public void sendEmail() throws Exception {
        EmailHelper emailHelper = factory.create();
        emailHelper.send("Hello 2", "Sample 2", "kpiyumal90@gmail.com");
        emailHelper.send("Hello 3", "Sample 3", "kpiyumal90@gmail.com");
    }
}