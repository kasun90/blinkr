package com.blink.admin.user;

import com.blink.common.AlbumHelper;
import com.blink.core.database.*;
import com.blink.core.file.FileService;
import com.blink.core.log.Logger;
import com.blink.core.service.BaseService;
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.album.*;
import com.blink.shared.admin.portal.UserDetailsRequestMessage;
import com.blink.shared.admin.portal.UserDetailsResponseMessage;
import com.blink.shared.admin.portal.UserMessagesRequestMessage;
import com.blink.shared.admin.portal.UserMessagesResponseMessage;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.common.Album;
import com.blink.shared.system.InvalidRequest;
import com.blink.utilities.BlinkTime;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserHandler {
    private BaseService adminService;
    private String username;
    private Logger logger;
    private AlbumHelper albumHelper;

    public UserHandler(String username, BaseService adminService) {
        this.adminService = adminService;
        this.username = username;
        logger = adminService.getContext().getLoggerFactory().getLogger(String.format("%s-%s", "User", username));
        this.albumHelper = new AlbumHelper(adminService);
    }

    public void handleMessage(Object message) throws Exception {
        if (message instanceof UserDetailsRequestMessage) {
            UserDetails user = adminService.getContext().getDbServiceFactory().ofCollection("adminUser").
                    find(new SimpleDBObject().append("username", username), UserDetails.class).first();
            if (user == null)
                adminService.sendReply( new InvalidRequest("No user found"));
            else {
                adminService.sendReply(new UserDetailsResponseMessage(user.getName(), user.getType().toString(), user.getEmail(), getProfilePicture()));
            }

        } else if (message instanceof UserMessagesRequestMessage) {
            handleUserMessagesRequest((UserMessagesRequestMessage) message);
        } else if (message instanceof AlbumsRequestMessage) {
            handleAlbumsReq((AlbumsRequestMessage) message);
        } else if (message instanceof AlbumKeyCheckRequestMessage) {
            handleAlbumKeyCheck((AlbumKeyCheckRequestMessage) message);
        } else if (message instanceof CreateAlbumRequestMessage) {
            handleCreateAlbum((CreateAlbumRequestMessage) message);
        } else if (message instanceof AlbumPhotoUploadMessage) {
            handleAlbumPhotoUpload((AlbumPhotoUploadMessage) message);
        } else if (message instanceof AlbumCoverUploadMessage) {
            handleAlbumCoverUpload((AlbumCoverUploadMessage) message);
        } else if (message instanceof AlbumDeleteMessage) {
            handleAlbumDelete((AlbumDeleteMessage) message);
        } else {
            adminService.error("Unhandled message received {}", message);
            adminService.sendReply(new InvalidRequest("Unhandled Message received"));
        }
    }

    private String getProfilePicture() throws Exception {
        FileService fileService = adminService.getContext().getFileService();
        String path = fileService.newFileURI().appendResource("media").appendResource("adminUser")
                .appendResource(username).appendResource("profile.jpg").build();
        if (!fileService.exists(path))
            return null;
        return fileService.getURL(path).toString();
    }

    private void handleUserMessagesRequest(UserMessagesRequestMessage req) throws Exception {
        DBService userMsgDB = adminService.getContext().getDbServiceFactory().ofCollection("userMessage");
        List<UserMessage> messages = new LinkedList<>();
        int limit = req.getLimit();
        int current = 0;
        if (req.getTimestamp() == 0L) {
            Iterator<UserMessage> iterator = userMsgDB.findAll(UserMessage.class, SortCriteria.descending("timestamp")).iterator();
            while (iterator.hasNext() && current < limit) {
                messages.add(iterator.next());
                current++;
            }
        } else {
            SimpleDBObject toFind = new SimpleDBObject();
            if (req.isLess())
                toFind.append("timestamp", req.getTimestamp(), Filter.LT);
            else
                toFind.append("timestamp", req.getTimestamp(), Filter.GT);

            Iterator<UserMessage> iterator = userMsgDB.find(toFind, UserMessage.class).iterator();

            while (iterator.hasNext() && current < limit) {
                messages.add(iterator.next());
                current++;
            }

        }
        adminService.sendReply(new UserMessagesResponseMessage(messages, userMsgDB.count(UserMessage.class)));
    }

    private void handleAlbumsReq(AlbumsRequestMessage message) throws Exception {
        adminService.sendReply(new AlbumsResponseMessage(albumHelper.getAlbums(message.getTimestamp(),
                message.isLess(), message.getLimit()), albumHelper.getAlbumsCount()));
    }

    private void handleAlbumKeyCheck( AlbumKeyCheckRequestMessage message) throws Exception {
        logger.info("Key Check {}", message);
        adminService.sendReply(new AlbumKeyCheckResponseMessage(albumHelper.isKeyAvailable(message.getKey())));
    }

    private void handleCreateAlbum(CreateAlbumRequestMessage message) throws Exception {
        logger.info("Create album request {}", message);
        AlbumHelper.AlbumBuilder builder = new AlbumHelper.AlbumBuilder();
        Album album = builder.setTitle(message.getTitle())
                .setKey(message.getKey())
                .setDescription(message.getDescription())
                .setTimestamp(BlinkTime.getCurrentTimeMillis())
                .build();
        albumHelper.saveAlbum(album);
        adminService.sendReply(new CreateAlbumResponseMessage(message.getKey(),
                true, "Success"));
    }

    private void handleAlbumPhotoUpload(AlbumPhotoUploadMessage uploadMessage) throws Exception {
        boolean success = albumHelper.savePhoto(uploadMessage.getKey(), uploadMessage.getFileContent());
        adminService.sendReply(new AlbumPhotoUploadResponseMessage(uploadMessage.getKey(), success));
    }

    private void handleAlbumCoverUpload(AlbumCoverUploadMessage message) throws Exception {
        boolean success = albumHelper.saveCover(message.getKey(), message.getFileContent());
        adminService.sendReply(new AlbumCoverUploadResponseMessage(message.getKey(), success));
    }

    private void handleAlbumDelete(AlbumDeleteMessage message) throws Exception {
        adminService.sendReply(new AlbumDeleteResponeMessage(message.getKey(),
                albumHelper.deleteAlbum(message.getKey())));
    }
}
