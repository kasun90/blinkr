package com.blink.admin.user;

import com.blink.admin.user.subhandler.*;
import com.blink.common.AlbumHelper;
import com.blink.common.ArticleHelper;
import com.blink.common.PresetHelper;
import com.blink.core.database.DBService;
import com.blink.core.database.Filter;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.database.SortCriteria;
import com.blink.core.file.FileService;
import com.blink.core.log.Logger;
import com.blink.core.service.BaseService;
import com.blink.core.setting.Setting;
import com.blink.core.setting.SettingHelper;
import com.blink.core.transport.Bus;
import com.blink.shared.admin.ActionResponseMessage;
import com.blink.shared.admin.FileUploadResponseMessage;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.album.*;
import com.blink.shared.admin.article.*;
import com.blink.shared.admin.portal.*;
import com.blink.shared.admin.preset.*;
import com.blink.shared.admin.setting.*;
import com.blink.shared.client.GenericStatusReplyMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.common.Album;
import com.blink.shared.common.Article;
import com.blink.shared.common.File;
import com.blink.shared.common.Preset;
import com.blink.shared.system.InvalidRequest;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserHandler {
    private BaseService adminService;
    private String username;
    private Logger logger;
    private Bus bus;

    public UserHandler(String username, BaseService adminService) {
        this.adminService = adminService;
        this.username = username;
        this.logger = adminService.getContext().getLoggerFactory().getLogger(String.format("%s-%s", "User", username));
        registerHandlers();
    }

    private void registerHandlers() {
        this.bus = adminService.getContext().getBus().createNew();
        this.bus.register(this);
        this.bus.register(new AlbumHandler(this));
        this.bus.register(new ArticleHandler(this));
        this.bus.register(new PresetHandler(this));
        this.bus.register(new ProfileHandler(this));
        this.bus.register(new SettingHandler(this));
        this.bus.register(new UserMessageHandler(this));
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

    public void handleMessage(Object message) throws Exception {
        this.bus.post(message);
    }

    @Subscribe
    public void onDeadEvent(DeadEvent e) {
        logger.info("Dead event received: {}", e.getEvent());
    }
}
