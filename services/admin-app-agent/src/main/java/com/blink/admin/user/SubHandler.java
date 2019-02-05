package com.blink.admin.user;

import com.blink.core.database.SimpleDBObject;
import com.blink.core.log.Logger;
import com.blink.core.messaging.Sender;
import com.blink.core.service.BaseService;
import com.blink.shared.admin.UserDetails;

public abstract class SubHandler {
    protected BaseService adminService;
    protected String username;
    protected Logger logger;
    protected Sender emailSender;

    public SubHandler(UserHandler userHandler) {
        this.adminService = userHandler.getAdminService();
        this.username = userHandler.getUsername();
        this.logger = userHandler.getLogger();
        this.emailSender = userHandler.getEmailSender();
    }

    protected UserDetails getUser() throws Exception {
        return adminService.getContext().getDbServiceFactory().ofCollection("adminUser").
                find(new SimpleDBObject().append("username", username), UserDetails.class).first();
    }
}
