package com.blink.admin.auth;

import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.log.Logger;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.admin.UserDetails;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthHelper {
    private Map<String, String> sessions = new ConcurrentHashMap<>();
    private DBService authDB;
    private BaseService adminService;

    public AuthHelper(BaseService adminService) {
        this.adminService = adminService;
        authDB = adminService.getContext().getDbService().withCollection("adminUser");
    }

    public boolean hasSession(String username) {
        return sessions.containsKey(username);
    }

    public boolean isValidSession(String username, String sessionID) {
        String session = sessions.get(username);
        if (session == null)
            return false;
        else return session.equals(sessionID);
    }

    public boolean validateLogin(String username, String password) throws Exception {
        if (username == null || password == null) {
            adminService.error("Username or password cannot be null");
            return false;
        }

        UserDetails user = authDB.find(new SimpleDBObject().append("username", username), UserDetails.class).first();

        if (user == null) {
            adminService.error("No user found for username: {}", username);
            return false;
        }

        return user.getPassword().equalsIgnoreCase(password);
    }

    public String createSession(String username) {
        String session = UUID.randomUUID().toString();
        sessions.put(username, session);
        return session;
    }
}
