package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.file.FileService;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.portal.*;
import com.blink.shared.system.InvalidRequest;
import xyz.justblink.eventbus.Subscribe;

public class ProfileHandler extends SubHandler {
    public ProfileHandler(UserHandler handler) {
        super(handler);
    }

    @Subscribe
    public void handleUserDetails(UserDetailsRequestMessage message) throws Exception {
        UserDetails user = getUser();
        if (user == null)
            adminService.sendReply(message.getRequestID(), new InvalidRequest("No user found"));
        else {
            adminService.sendReply(message.getRequestID(), new UserDetailsResponseMessage(user.getName(), user.getType().toString(), user.getEmail(), getProfilePicture()));
        }
    }

    @Subscribe
    public void handleChangeName(ChangeNameMessage message) throws Exception {
        logger.info("Change name request received for user: {}", username);
        UserDetails user = getUser();
        ChangeNameResponseMessage response = new ChangeNameResponseMessage();
        if (user == null)
            response.setSuccess(false).setDescription("No User Found");
        else {
            user.setName(message.getNewName());
            updateUser(user);
            response.setSuccess(true).setDescription("Success");
            logger.info("Name changed for user: {}", username);
        }
        adminService.sendReply(message.getRequestID(), response);
    }

    @Subscribe
    public void handleChangePassword(ChangePasswordMessage message) throws Exception {
        logger.info("Change password request received for user: {}", username);
        UserDetails user = getUser();
        ChangePasswordResponseMessage response = new ChangePasswordResponseMessage();
        if (user == null)
            response.setSuccess(false).setDescription("No user Found");
        else if (message.getOldPassword() == null || message.getNewPassword() == null)
            response.setSuccess(false).setDescription("Passwords cannot be null");
        else if (!user.getPassword().equalsIgnoreCase(message.getOldPassword()))
            response.setSuccess(false).setDescription("Password not correct");
        else {
            logger.info("Password matched. Proceeding to change.");
            user.setPassword(message.getNewPassword());
            updateUser(user);
            response.setSuccess(true).setDescription("Password changed Successfully");
        }
        adminService.sendReply(message.getRequestID(), response);
    }

    private String getProfilePicture() throws Exception {
        FileService fileService = adminService.getContext().getFileService();
        String path = fileService.newFileURI().appendResource("media").appendResource("adminUser")
                .appendResource(username).appendResource("profile.jpg").build();
        if (!fileService.exists(path))
            return null;
        return fileService.getURL(path).toString();
    }

    private void updateUser(UserDetails user) throws Exception {
        adminService.getContext().getDbServiceFactory().ofCollection("adminUser")
                .insertOrUpdate(new SimpleDBObject().append("username", username), user);
    }
}
