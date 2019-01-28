package com.blink.email.helper;

import java.util.Map;

public interface EmailTemplateDataProvider {
    void setEmail(String email) throws Exception;
    Map<String, String> get();
    EmailTemplateDataProvider with(Map<String, String> data);
    void reset();
}
