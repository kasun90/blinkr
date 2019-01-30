package com.blink.email.helper;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.shared.email.EmailType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTemplateResolver implements EmailTemplateResolver {

    private Map<EmailType, String> templateMap = new HashMap<>();

    {
        templateMap.put(EmailType.NEW_SUBSCRIBE, "/templates/new_subscribe.html");
    }

    @Override
    public String getBody(EmailType type, EmailTemplateDataProvider provider) throws Exception {
        String template = templateMap.get(type);

        if (template == null)
            throw new BlinkRuntimeException(MessageFormat.format("Cannot find template for the type: {0}", type.name()));

        StringBuilder builder = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(template)))) {
            while ((line = br.readLine()) != null)
                builder.append(line);
        }

        String body = builder.toString();
        Map<String, String> dataMap = provider.get();

        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = dataMap.get(key);
            if (value != null)
                body = body.replace(matcher.group(0), value);
        }
        return body;
    }
}
