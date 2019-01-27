package com.blink.email.helper;

import com.blink.shared.email.EmailType;

import java.util.Map;

public class FileTemplateResolver implements EmailTemplateResolver {
    @Override
    public String getBody(EmailType type, Map<String, String> data) throws Exception {
        return null;
    }
}
