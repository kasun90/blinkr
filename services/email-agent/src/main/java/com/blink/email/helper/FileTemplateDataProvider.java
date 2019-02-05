package com.blink.email.helper;

import com.blink.core.file.FileService;
import com.blink.core.service.Configuration;
import com.blink.core.service.Context;
import org.apache.http.client.utils.URIBuilder;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class FileTemplateDataProvider implements EmailTemplateDataProvider {

    private Map<String, String> defaultData = new HashMap<>();
    private Map<String, String> currentData;
    private String baseURL;

    public FileTemplateDataProvider(Context context) throws Exception {
        FileService fileService = context.getFileService();
        defaultData.put("blink_logo",
                fileService.getURL(fileService.newFileURI().appendResource("media/email/logo.png").build()).toString());
        defaultData.put("instagram_icon",
                fileService.getURL(fileService.newFileURI().appendResource("media/email/icons/instagram.png").build()).toString());
        defaultData.put("facebook_icon",
                fileService.getURL(fileService.newFileURI().appendResource("media/email/icons/facebook.png").build()).toString());
        URIBuilder builder = new URIBuilder();
        Configuration configuration = context.getConfiguration();
        Boolean secured = configuration.getValue("secured", Boolean.class);
        builder.setScheme(secured ? "https" : "http");
        builder.setHost(configuration.getDomain());
        Boolean withPort = configuration.getValue("withPort", Boolean.class);
        if (withPort)
            builder.setPort(configuration.getClientPort());
        baseURL = builder.build().toString();
    }

    public FileTemplateDataProvider(Map<String, String> defaultData, String baseURL) {
        this.defaultData = defaultData;
        this.baseURL = baseURL;
    }

    @Override
    public void setEmail(String email) throws Exception {
        URIBuilder builder = new URIBuilder(baseURL);
        builder.setPath("action/unsubscribe");
        builder.addParameter("email", email);
        defaultData.put("unsub_link", builder.build().toString());
    }


    @Override
    public Map<String, String> get() {
        currentData.putAll(defaultData);
        return currentData;
    }

    @Override
    public String getSiteURL(String format, Object... args) throws Exception {
        String path = MessageFormat.format(format, args);
        URIBuilder builder = new URIBuilder(baseURL);
        builder.setPath(path);
        return builder.build().toString();
    }


    @Override
    public EmailTemplateDataProvider with(Map<String, String> data) {
        if (data == null)
            currentData = new HashMap<>();
        else
            currentData = data;
        return this;
    }

    @Override
    public void reset() {
        currentData = null;
    }
}
