package com.blink.admin.user;

import com.blink.core.database.SimpleDBObject;
import com.blink.core.log.Logger;
import com.blink.core.service.BaseService;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.portal.UserDetailsRequestMessage;
import com.blink.shared.admin.portal.UserDetailsResponseMessage;
import com.blink.shared.system.InvalidRequest;

public class UserHandler {
    private BaseService adminService;
    private String username;
    private Logger logger;

    public UserHandler(String username, BaseService adminService) {
        this.adminService = adminService;
        this.username = username;
        logger = adminService.getContext().getLoggerFactory().getLogger(String.format("%s-%s", "User", username));
    }

    public void handleMessage(String requestID, Object message) throws Exception {
        if (message instanceof UserDetailsRequestMessage) {
            UserDetails user = adminService.getContext().getDbService().withCollection("adminUser").
                    find(new SimpleDBObject().append("username", username), UserDetails.class).first();
            if (user == null)
                adminService.sendReply(requestID, new InvalidRequest("No user found"));
            else
                adminService.sendReply(requestID, new UserDetailsResponseMessage(user.getName(), user.getType().toString(), user.getEmail()));
        }
    }
}
