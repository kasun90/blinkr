package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.common.PresetHelper;
import com.blink.shared.admin.preset.*;
import com.blink.shared.common.Preset;
import com.blink.shared.email.BulkEmailQueueMessage;
import com.blink.shared.email.EmailType;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class PresetHandler extends SubHandler {
    private PresetHelper presetHelper;

    public PresetHandler(UserHandler userHandler) {
        super(userHandler);
        this.presetHelper = new PresetHelper(adminService.getContext());
    }

    @Subscribe
    public void handlePresetsReq(PresetsRequestMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new PresetsResponseMessage(presetHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), presetHelper.getEntityCount()));
    }

    @Subscribe
    public void handlePresetKeyCheck(PresetKeyCheckRequestMessage message) throws Exception {
        logger.info("Preset key check {}", message);
        adminService.sendReply(message.getRequestID(), new PresetKeyCheckResponseMessage(presetHelper.isKeyAvailable(message.getKey())));
    }

    @Subscribe
    public void handlePresetCreate(CreatePresetRequestMessage message) throws Exception {
        logger.info("Preset create requset {}", message);
        Preset newPreset = new Preset();
        newPreset.setKey(message.getKey())
                .setTitle(message.getTitle())
                .setDescription(message.getDescription())
                .setTimestamp(BlinkTime.getCurrentTimeMillis());
        presetHelper.saveEntity(newPreset);
        adminService.sendReply(message.getRequestID(), new CreatePresetResponseMessage(message.getKey(),
                true, "Success"));
    }

    @Subscribe
    public void handlePresetTemplateUpload(PresetTemplateUploadMessage message) throws Exception {
        boolean success = presetHelper.saveTemplateFile(message.getKey(), message.getContent(), message.getFileName());
        adminService.sendReply(message.getRequestID(), new PresetTemplateUploadResponseMessage(message.getKey(), success));
    }

    @Subscribe
    public void handlePresetBeforePhotoUpload(PresetBeforePhotoUploadMessage message) throws Exception {
        boolean success = presetHelper.saveBeforeImage(message.getKey(), message.getContent());
        adminService.sendReply(message.getRequestID(), new PresetBeforePhotoUploadResponseMessage(message.getKey(), success));
    }

    @Subscribe
    public void handlePresetAfterPhotoUpload(PresetAfterPhotoUploadMessage message) throws Exception {
        boolean success = presetHelper.saveAfterImage(message.getKey(), message.getContent());
        adminService.sendReply(message.getRequestID(), new PresetAfterPhotoUploadResponseMessage(message.getKey(), success));

        Preset preset = presetHelper.getDetailsEntity(message.getKey());
        Map<String, String> data = new HashMap<>();
        data.put("key", preset.getKey());
        data.put("preset_name", preset.getTitle());
        emailSender.send(new BulkEmailQueueMessage(EmailType.NEW_PRESET, MessageFormat.format("New Preset: {0}", preset.getTitle()),
                data));
        logger.info("New preset email notification queued [key={}]", preset.getKey());
    }

    @Subscribe
    public void handlePresetDelete(PresetDeleteMessage message) throws Exception {
        boolean success = presetHelper.deleteEntity(message.getKey());
        adminService.sendReply(message.getRequestID(), new PresetDeleteResponeMessage(message.getKey(), success));
        logger.info("Preset delete status [sucess={} key={}]", success, message.getKey());
    }
}
