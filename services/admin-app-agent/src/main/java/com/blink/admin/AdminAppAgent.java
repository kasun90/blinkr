package com.blink.admin;

import com.blink.admin.auth.AuthHelper;
import com.blink.admin.user.UserHandler;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.admin.AdminRequestMessage;
import com.blink.shared.admin.portal.LoginMessage;
import com.blink.shared.admin.portal.LoginReplyMessage;
import com.blink.shared.system.InvalidRequest;
import com.google.common.eventbus.Subscribe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class AdminAppAgent extends BaseService {

    private AuthHelper authHelper;
    private Map<String, UserHandler> userCache = new ConcurrentHashMap<>();

    public AdminAppAgent(Context context) {
        super(context);
        authHelper = new AuthHelper(this);
    }

    @Subscribe
    private void onAdminRequest(AdminRequestMessage message) throws Exception {
        Object enclosedMessage = message.getEnclosedMessage();
        String requestID = message.getRequestID();

        if (enclosedMessage instanceof LoginMessage) {
            onLogin(requestID, LoginMessage.class.cast(enclosedMessage));
        } else if (authHelper.isValidSession(message.getTargetUser(), message.getSessionID())) {
            UserHandler userHandler = userCache.get(message.getTargetUser());
            if (userHandler == null) {
                userHandler = new UserHandler(message.getTargetUser(), this);
                userCache.put(message.getTargetUser(), userHandler);
            }
            userHandler.handleMessage(requestID, enclosedMessage);
        } else {
            error("Unauthorized message received [sessionID={} message={}]", message.getSessionID(), enclosedMessage);
            sendReply(requestID, new InvalidRequest("Invalid Request"));
        }
    }

    private void onLogin(String requestID, LoginMessage message) throws Exception {
        if (authHelper.validateLogin(message.getUsername(), message.getPassword())) {
            info("Login success for user: {}", message.getUsername());
            String sessionID = authHelper.createSession(message.getUsername());
            sendReply(requestID, new LoginReplyMessage(sessionID, 0, "Success"));
        } else {
            sendReply(requestID, new LoginReplyMessage(null, 100, "Username or Password is incorrect"));
        }
    }

    @Override
    public String getServiceName() {
        return "ADMIN";
    }
}
