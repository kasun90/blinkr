package com.blink.email.helper.mailgun;

import com.blink.core.log.Logger;
import com.blink.core.service.Context;
import com.blink.core.setting.SettingReader;
import com.blink.email.helper.EmailHelper;
import com.blink.email.helper.EmailHelperFactory;

import java.util.Properties;

public class MailgunEmailHelperFactory implements EmailHelperFactory {

    private Properties properties;
    private Logger logger;

    public MailgunEmailHelperFactory(Context context) throws Exception {
        this.logger = context.getLoggerFactory().getLogger("MAIL-HELPER");
        SettingReader settingReader = context.getDerivedService(SettingReader.class);
        properties = new Properties();
        properties.put("mail.smtps.host", settingReader.getSetting("smtp_host", "smtp.mailgun.org"));
        properties.put("mail.smtp.user", settingReader.getSetting("smtp_user", "default"));
        properties.put("mail.smtp.password", settingReader.getSetting("smtp_password", "default"));
        properties.put("mail.smtps.auth","true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.default", settingReader.getSetting("default_from_mail", "hello@justblink.xyz"));
    }

    public MailgunEmailHelperFactory(Properties properties, Logger logger) {
        this.properties = properties;
        this.logger = logger;
    }


    @Override
    public EmailHelper create() throws Exception {
        return new MailgunEmailHelper(properties, logger);
    }
}
