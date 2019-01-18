package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.common.PresetHelper;
import com.blink.shared.admin.preset.*;
import com.blink.shared.common.Preset;
import com.blink.utilities.BlinkTime;
import com.google.common.eventbus.Subscribe;

public class PresetHandler extends SubHandler {
    private PresetHelper presetHelper;

    public PresetHandler(UserHandler userHandler) {
        super(userHandler);
        this.presetHelper = new PresetHelper(adminService.getContext());
    }

    @Subscribe
    public void handlePresetsReq(PresetsRequestMessage message) throws Exception {
        adminService.sendReply(new PresetsResponseMessage(presetHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), presetHelper.getEntityCount()));
    }

    @Subscribe
    public void handlePresetKeyCheck(PresetKeyCheckRequestMessage message) throws Exception {
        logger.info("Preset key check {}", message);
        adminService.sendReply(new PresetKeyCheckResponseMessage(presetHelper.isKeyAvailable(message.getKey())));
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
        adminService.sendReply(new CreatePresetResponseMessage(message.getKey(),
                true, "Success"));
    }

//    @Subscribe
//    public void handlePresetTemplateUpload(PresetTemplateUploadMessage message) throws Exception {
//        boolean success = presetHelper.saveTemplateFile(message.getKey(), message.getContent(), message.getFileName());
//        adminService.sendReply(new PresetTemplateUploadResponseMessage(message.getKey(), success));
//    }

//    @Subscribe
//    public void handlePresetBeforePhotoUpload(PresetBeforePhotoUploadMessage message) throws Exception {
//        boolean success = presetHelper.saveBeforeImage(message.getKey(), message.getContent());
//        adminService.sendReply(new PresetBeforePhotoUploadResponseMessage(message.getKey(), success));
//    }

//    @Subscribe
//    public void handlePresetAfterPhotoUpload(PresetAfterPhotoUploadMessage message) throws Exception {
//        boolean success = presetHelper.saveAfterImage(message.getKey(), message.getContent());
//        adminService.sendReply(new PresetAfterPhotoUploadResponseMessage(message.getKey(), success));
//    }

    @Subscribe
    public void handlePresetDelete(PresetDeleteMessage message) throws Exception {
        boolean success = presetHelper.deleteEntity(message.getKey());
        adminService.sendReply(new PresetDeleteResponeMessage(message.getKey(), success));
        logger.info("Preset delete status [sucess={} key={}]", success, message.getKey());
    }
}
