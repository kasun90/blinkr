package com.blink.admin;

import com.blink.admin.auth.AuthHelper;
import com.blink.admin.user.UserHandler;
import com.blink.common.BlinkService;
import com.blink.core.service.Context;
import com.blink.shared.admin.AdminRequestMessage;
import com.blink.shared.admin.portal.LoginMessage;
import com.blink.shared.admin.portal.LoginReplyMessage;
import com.blink.shared.system.InvalidRequest;
import com.google.common.eventbus.Subscribe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdminAppAgent extends BlinkService {

    private AuthHelper authHelper;
    private Map<String, UserHandler> userCache = new ConcurrentHashMap<>();

    public AdminAppAgent(Context context) {
        super(context);
        authHelper = new AuthHelper(this);
    }

    @Subscribe
    private void onAdminRequest(AdminRequestMessage message) throws Exception {
        Object enclosedMessage = message.getEnclosedMessage();
        setRequestID(message.getRequestID());

        if (enclosedMessage instanceof LoginMessage) {
            onLogin((LoginMessage) enclosedMessage);
        } else if (authHelper.isValidSession(message.getTargetUser(), message.getSessionID())) {
            UserHandler userHandler = userCache.get(message.getTargetUser());
            if (userHandler == null) {
                userHandler = new UserHandler(message.getTargetUser(), this);
                userCache.put(message.getTargetUser(), userHandler);
            }
            userHandler.handleMessage(enclosedMessage);
        } else {
            error("Unauthorized message received [sessionID={} message={}]", message.getSessionID(), enclosedMessage);
            sendReply(new InvalidRequest("Invalid Request"));
        }
    }

    private void onLogin(LoginMessage message) throws Exception {
        if (authHelper.validateLogin(message.getUsername(), message.getPassword())) {
            info("Login success for user: {}", message.getUsername());
            String sessionID = authHelper.createSession(message.getUsername());
            sendReply(new LoginReplyMessage(sessionID, 0, "Success"));
        } else {
            sendReply(new LoginReplyMessage(null, 100, "Username or Password is incorrect"));
        }
    }

    @Override
    public String getServiceName() {
        return "ADMIN";
    }
}
