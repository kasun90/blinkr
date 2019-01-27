package com.blink.email.helper;

import com.blink.shared.email.EmailType;

import java.util.Map;

public interface EmailTemplateResolver {
    String getBody(EmailType type, Map<String, String> data) throws Exception;
}
