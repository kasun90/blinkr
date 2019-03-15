package com.blink.admin.user;

import com.blink.admin.user.subhandler.*;
import com.blink.core.log.Logger;
import com.blink.core.messaging.Sender;
import com.blink.core.service.BaseService;
import com.blink.core.transport.Bus;
import xyz.justblink.eventbus.DeadEvent;
import xyz.justblink.eventbus.Subscribe;

public class UserHandler {
    private BaseService adminService;
    private String username;
    private Logger logger;
    private Bus bus;
    private Sender emailSender;

    public UserHandler(String username, BaseService adminService) throws Exception {
        this.adminService = adminService;
        this.username = username;
        this.logger = adminService.getContext().getLoggerFactory().getLogger(String.format("%s-%s", "User", username));
        this.emailSender = adminService.getContext().getMessagingService().createSender("email");
        registerHandlers();
    }

    private void registerHandlers() {
        this.bus = adminService.getContext().getBusService().createNew();
        this.bus.register(this);
        this.bus.register(new AlbumHandler(this));
        this.bus.register(new ArticleHandler(this));
        this.bus.register(new PresetHandler(this));
        this.bus.register(new ProfileHandler(this));
        this.bus.register(new SettingHandler(this));
        this.bus.register(new UserMessageHandler(this));
        this.bus.register(new SubscriptionHandler(this));
    }

    String getUsername() {
        return username;
    }

    BaseService getAdminService() {
        return adminService;
    }

    public Logger getLogger() {
        return logger;
    }

    Sender getEmailSender() {
        return emailSender;
    }

    public void handleMessage(Object message) throws Exception {
        this.bus.post(message);
    }

    @Subscribe
    public void onDeadEvent(DeadEvent e) {
        logger.info("Dead event received: {}", e.getEvent());
    }
}
