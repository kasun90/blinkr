package com.blink.admin.user;

import com.blink.core.database.*;
import com.blink.core.file.FileService;
import com.blink.core.log.Logger;
import com.blink.core.service.BaseService;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.portal.UserDetailsRequestMessage;
import com.blink.shared.admin.portal.UserDetailsResponseMessage;
import com.blink.shared.admin.portal.UserMessagesRequestMessage;
import com.blink.shared.admin.portal.UserMessagesResponseMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.system.InvalidRequest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
            UserDetails user = adminService.getContext().getDbServiceFactory().ofCollection("adminUser").
                    find(new SimpleDBObject().append("username", username), UserDetails.class).first();
            if (user == null)
                adminService.sendReply(requestID, new InvalidRequest("No user found"));
            else {
                adminService.sendReply(requestID, new UserDetailsResponseMessage(user.getName(), user.getType().toString(), user.getEmail(), getProfilePicture()));
            }

        } else if (message instanceof UserMessagesRequestMessage) {
            handleUserMessagesRequest(requestID, UserMessagesRequestMessage.class.cast(message));
        } else {
            adminService.error("Unhandled message received {}", message);
            adminService.sendReply(requestID, new InvalidRequest("Unhandled Message received"));
        }
    }

    private String getProfilePicture() throws Exception {
        FileService fileService = adminService.getContext().getFileService();
        String path = fileService.newFileURI().appendResource("media").appendResource("adminUser")
                .appendResource(username).appendResource("profile.jpg").build();
        if (!fileService.exists(path))
            return null;
        return fileService.getURL(path).toString();
    }

    private void handleUserMessagesRequest(String requestID, UserMessagesRequestMessage req) throws Exception {
        DBService userMsgDB = adminService.getContext().getDbServiceFactory().ofCollection("userMessage");
        List<UserMessage> messages = new LinkedList<>();
        int limit = req.getLimit();
        int current = 0;
        if (req.getTimestamp() == 0L) {
            Iterator<UserMessage> iterator = userMsgDB.findAll(UserMessage.class, SortCriteria.descending("timestamp")).iterator();
            while (iterator.hasNext() && current < limit) {
                messages.add(iterator.next());
                current++;
            }
        } else {
            SimpleDBObject toFind = new SimpleDBObject();
            if (req.isLess())
                toFind.append("timestamp", req.getTimestamp(), Filter.LT);
            else
                toFind.append("timestamp", req.getTimestamp(), Filter.GT);

            Iterator<UserMessage> iterator = userMsgDB.find(toFind, UserMessage.class).iterator();

            while (iterator.hasNext() && current < limit) {
                messages.add(iterator.next());
                current++;
            }

        }
        adminService.sendReply(requestID, new UserMessagesResponseMessage(messages, userMsgDB.count(UserMessage.class)));
    }
}
