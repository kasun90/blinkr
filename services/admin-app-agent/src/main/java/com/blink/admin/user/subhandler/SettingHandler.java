package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.core.setting.Setting;
import com.blink.core.setting.SettingHelper;
import com.blink.shared.admin.setting.*;
import com.blink.shared.client.GenericStatusReplyMessage;
import com.google.common.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

public class SettingHandler extends SubHandler {
    private SettingHelper settingHelper;

    public SettingHandler(UserHandler userHandler) {
        super(userHandler);
        this.settingHelper = adminService.getContext().getDerivedService(SettingHelper.class);
    }

    @Subscribe
    public void handleSettingRequest(SettingRequestMessage message) throws Exception {
        List<SettingExp> settings = new LinkedList<>();
        settingHelper.getSettings().forEach(setting -> {
            SettingExp settingExp = new SettingExp();
            settingExp.setKey(setting.getKey());
            settingExp.setValue(setting.getValue());
            settings.add(settingExp);
        });
        adminService.sendReply(message.getRequestID(), new SettingResponseMessage(settings));
    }

    @Subscribe
    public void handleNewSetting(NewSettingMessage message) throws Exception {
        if (message.getKey() == null || message.getValue() == null) {
            adminService.sendReply(new GenericStatusReplyMessage(false, "Invalid values"));
            return;
        }

        Setting setting = settingHelper.getSetting(message.getKey());
        if (setting != null)
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(false, "Setting already available"));
        else {
            settingHelper.store(message.getKey(), message.getValue());
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(true, "Success"));
            logger.info("New setting added [key={}]", message.getKey());
        }
    }

    @Subscribe
    public void handleUpdateSetting(UpdateSettingMessage message) throws Exception {
        if (message.getKey() == null || message.getValue() == null) {
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(false, "Invalid values"));
            return;
        }

        Setting setting = settingHelper.getSetting(message.getKey());
        if (setting == null)
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(false, "Setting not available"));
        else {
            settingHelper.store(message.getKey(), message.getValue());
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(true, "Success"));
            logger.info("Setting updated [key={}]", message.getKey());
        }
    }

    @Subscribe
    public void handleDeleteSetting(DeleteSettingMessage message) throws Exception {
        if (message.getKey() == null) {
            adminService.sendReply(message.getRequestID(), new GenericStatusReplyMessage(false, "Invalid value"));
            return;
        }

        settingHelper.deleteSetting(message.getKey());
        adminService.sendReply(message.getRequestID(), new DeleteSettingResponseMessage(message.getKey()));
        logger.info("Setting deleted [key={}]", message.getKey());
    }
}
