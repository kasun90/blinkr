package com.blink.admin;

import com.blink.admin.auth.AuthHelper;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.admin.AdminRequestMessage;
import com.blink.shared.admin.portal.LoginMessage;
import com.blink.shared.admin.portal.LoginReplyMessage;
import com.google.common.eventbus.Subscribe;

public class AdminAppAgent extends BaseService {

    private AuthHelper authHelper;

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
        }
    }

    private void onLogin(String requestID, LoginMessage message) throws Exception {
        if (authHelper.validateLogin(message.getUsername(), message.getPassword())) {
            info("Login success for user: {}", message.getUsername());
            String session = authHelper.createSession(message.getUsername());
            sendReply(requestID, new LoginReplyMessage(session, 0, "Success"));
        } else {
            sendReply(requestID, new LoginReplyMessage(null, 100, "Username or Password is incorrect"));
        }
    }

    @Override
    public String getServiceName() {
        return "ADMIN";
    }
}
