package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.common.AlbumHelper;
import com.blink.shared.admin.album.*;
import com.blink.shared.common.Album;
import com.blink.shared.email.BulkEmailQueueMessage;
import com.blink.shared.email.EmailType;
import com.blink.utilities.BlinkTime;
import xyz.justblink.eventbus.Subscribe;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class AlbumHandler extends SubHandler {
    private AlbumHelper albumHelper;

    public AlbumHandler(UserHandler userHandler) {
        super(userHandler);
        this.albumHelper = new AlbumHelper(adminService.getContext());
    }

    @Subscribe
    public void handleAlbumsReq(AlbumsRequestMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new AlbumsResponseMessage(albumHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), albumHelper.getEntityCount()));
    }

    @Subscribe
    public void handleAlbumKeyCheck(AlbumKeyCheckRequestMessage message) throws Exception {
        logger.info("Key Check {}", message);
        adminService.sendReply(message.getRequestID(), new AlbumKeyCheckResponseMessage(albumHelper.isKeyAvailable(message.getKey())));
    }

    @Subscribe
    public void handleCreateAlbum(CreateAlbumRequestMessage message) throws Exception {
        logger.info("Create album request {}", message);
        AlbumHelper.AlbumBuilder builder = new AlbumHelper.AlbumBuilder();
        Album album = builder.setTitle(message.getTitle())
                .setKey(message.getKey())
                .setDescription(message.getDescription())
                .setTimestamp(BlinkTime.getCurrentTimeMillis())
                .build();
        albumHelper.saveEntity(album);
        adminService.sendReply(message.getRequestID(), new CreateAlbumResponseMessage(message.getKey(),
                true, "Success"));
    }

    @Subscribe
    public void handleAlbumPhotoUpload(AlbumPhotoUploadMessage uploadMessage) throws Exception {
        boolean success = albumHelper.savePhoto(uploadMessage.getKey(), uploadMessage.getFileContent());
        adminService.sendReply(uploadMessage.getRequestID(), new AlbumPhotoUploadResponseMessage(uploadMessage.getKey(), success));
    }

    @Subscribe
    public void handleAlbumCoverUpload(AlbumCoverUploadMessage message) throws Exception {
        boolean success = albumHelper.saveCover(message.getKey(), message.getFileContent());
        adminService.sendReply(message.getRequestID(), new AlbumCoverUploadResponseMessage(message.getKey(), success));
        logger.info("Cover photo uploaded [key={}]", message.getKey());

        Album album = albumHelper.getDetailsEntity(message.getKey());
        Map<String, String> data = new HashMap<>();
        data.put("cover_url", album.getCover().getUrl());
        data.put("album_name", album.getTitle());
        data.put("key", album.getKey());
        emailSender.send(new BulkEmailQueueMessage(EmailType.NEW_ALBUM, MessageFormat.format("New Album: {0}", album.getTitle()), data));
        logger.info("New album email notification queued [key={}]", message.getKey());
    }


    @Subscribe
    public void handleAlbumDelete(AlbumDeleteMessage message) throws Exception {
        boolean success = albumHelper.deleteEntity(message.getKey());
        adminService.sendReply(message.getRequestID(), new AlbumDeleteResponeMessage(message.getKey(),
                success));
        logger.info("Album delete status [success={} key={}]", success, message.getKey());
    }
}
