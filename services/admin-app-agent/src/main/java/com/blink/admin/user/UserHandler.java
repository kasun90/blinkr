package com.blink.admin.user;

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
import com.blink.shared.admin.UserDetails;
import com.blink.shared.admin.album.*;
import com.blink.shared.admin.article.*;
import com.blink.shared.admin.portal.*;
import com.blink.shared.admin.preset.*;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.shared.common.Album;
import com.blink.shared.common.Article;
import com.blink.shared.common.Preset;
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
    private PresetHelper presetHelper;
    private ArticleHelper articleHelper;

    public UserHandler(String username, BaseService adminService) {
        this.adminService = adminService;
        this.username = username;
        logger = adminService.getContext().getLoggerFactory().getLogger(String.format("%s-%s", "User", username));
        this.albumHelper = new AlbumHelper(adminService.getContext());
        this.presetHelper = new PresetHelper(adminService.getContext());
        this.articleHelper = new ArticleHelper(adminService.getContext());
    }

    public void handleMessage(Object message) throws Exception {
        if (message instanceof UserDetailsRequestMessage) {
            UserDetails user = getUser();
            if (user == null)
                adminService.sendReply(new InvalidRequest("No user found"));
            else {
                adminService.sendReply(new UserDetailsResponseMessage(user.getName(), user.getType().toString(), user.getEmail(), getProfilePicture()));
            }

        } else if (message instanceof ChangePasswordMessage) {
            handleChangePassword(((ChangePasswordMessage) message));
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
        } else if (message instanceof PresetsRequestMessage) {
            handlePresetsReq(((PresetsRequestMessage) message));
        } else if (message instanceof PresetKeyCheckRequestMessage) {
            handlePresetKeyCheck(((PresetKeyCheckRequestMessage) message));
        } else if (message instanceof CreatePresetRequestMessage) {
            handlePresetCreate(((CreatePresetRequestMessage) message));
        } else if (message instanceof PresetTemplateUploadMessage) {
            handlePresetTemplateUpload(((PresetTemplateUploadMessage) message));
        } else if (message instanceof PresetBeforePhotoUploadMessage) {
            handlePresetBeforePhotoUpload(((PresetBeforePhotoUploadMessage) message));
        } else if (message instanceof PresetAfterPhotoUploadMessage) {
            handlePresetAfterPhotoUpload(((PresetAfterPhotoUploadMessage) message));
        } else if (message instanceof PresetDeleteMessage) {
            handlePresetDelete(((PresetDeleteMessage) message));
        } else if (message instanceof ArticlesRequestMessage) {
            handleArticlesReq(((ArticlesRequestMessage) message));
        } else if (message instanceof ArticleKeyCheckRequestMessage) {
            handleArticleKeyCheck(((ArticleKeyCheckRequestMessage) message));
        } else if (message instanceof CreateArticleRequestMessage) {
            handleArticleCreate(((CreateArticleRequestMessage) message));
        } else if (message instanceof ArticleDeleteMessage) {
            handleArticleDelete(((ArticleDeleteMessage) message));
        } else {
            logger.error("Unhandled message received {}", message);
            adminService.sendReply(new InvalidRequest("Unhandled Message received"));
        }
    }

    private void handleChangePassword(ChangePasswordMessage message) throws Exception {
        logger.info("Change password request received for user: {}", username);
        UserDetails user = getUser();
        ChangePasswordResponseMessage response = new ChangePasswordResponseMessage();
        if (user == null)
            response.setSuccess(false).setDescription("No user Found");
        else if (message.getOldPassword() == null || message.getNewPassword() == null)
            response.setSuccess(false).setDescription("Passwords cannot be null");
        else if (!user.getPassword().equalsIgnoreCase(message.getOldPassword()))
            response.setSuccess(false).setDescription("Password not correct");
        else {
            logger.info("Password matched. Proceeding to change.");
            user.setPassword(message.getNewPassword());
            updateUser(user);
            response.setSuccess(true).setDescription("Password changed Successfully");
        }
        adminService.sendReply(response);
    }

    private UserDetails getUser() throws Exception {
        return adminService.getContext().getDbServiceFactory().ofCollection("adminUser").
                find(new SimpleDBObject().append("username", username), UserDetails.class).first();
    }

    private void updateUser(UserDetails user) throws Exception {
        adminService.getContext().getDbServiceFactory().ofCollection("adminUser")
                .insertOrUpdate(new SimpleDBObject().append("username", username), user);
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
        adminService.sendReply(new AlbumsResponseMessage(albumHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), albumHelper.getEntityCount()));
    }

    private void handleAlbumKeyCheck(AlbumKeyCheckRequestMessage message) throws Exception {
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
        albumHelper.saveEntity(album);
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
        logger.info("Cover photo uploaded [key={}]", message.getKey());
    }

    private void handleAlbumDelete(AlbumDeleteMessage message) throws Exception {
        boolean success = albumHelper.deleteEntity(message.getKey());
        adminService.sendReply(new AlbumDeleteResponeMessage(message.getKey(),
                success));
        logger.info("Album delete status [success={} key={}]", success, message.getKey());
    }

    private void handlePresetsReq(PresetsRequestMessage message) throws Exception {
        adminService.sendReply(new PresetsResponseMessage(presetHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), presetHelper.getEntityCount()));
    }

    private void handlePresetKeyCheck(PresetKeyCheckRequestMessage message) throws Exception {
        logger.info("Preset key check {}", message);
        adminService.sendReply(new PresetKeyCheckResponseMessage(presetHelper.isKeyAvailable(message.getKey())));
    }

    private void handlePresetCreate(CreatePresetRequestMessage message) throws Exception {
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

    private void handlePresetTemplateUpload(PresetTemplateUploadMessage message) throws Exception {
        boolean success = presetHelper.saveTemplateFile(message.getKey(), message.getContent(), message.getFileName());
        adminService.sendReply(new PresetTemplateUploadResponseMessage(message.getKey(), success));
    }

    private void handlePresetBeforePhotoUpload(PresetBeforePhotoUploadMessage message) throws Exception {
        boolean success = presetHelper.saveBeforeImage(message.getKey(), message.getContent());
        adminService.sendReply(new PresetBeforePhotoUploadResponseMessage(message.getKey(), success));
    }

    private void handlePresetAfterPhotoUpload(PresetAfterPhotoUploadMessage message) throws Exception {
        boolean success = presetHelper.saveAfterImage(message.getKey(), message.getContent());
        adminService.sendReply(new PresetAfterPhotoUploadResponseMessage(message.getKey(), success));
    }

    private void handlePresetDelete(PresetDeleteMessage message) throws Exception {
        boolean success = presetHelper.deleteEntity(message.getKey());
        adminService.sendReply(new PresetDeleteResponeMessage(message.getKey(), success));
        logger.info("Preset delete status [sucess={} key={}]", success, message.getKey());
    }

    private void handleArticlesReq(ArticlesRequestMessage message) throws Exception {
        adminService.sendReply(new ArticlesResponseMessage(articleHelper.getEntities(message.getTimestamp(),
                message.isLess(), message.getLimit()), articleHelper.getEntityCount()));
    }

    private void handleArticleKeyCheck(ArticleKeyCheckRequestMessage message) throws Exception {
        logger.info("Article key check: {}", message);
        adminService.sendReply(new ArticleKeyCheckResponseMessage(articleHelper.isKeyAvailable(message.getKey())));
    }

    private void handleArticleCreate(CreateArticleRequestMessage message) throws Exception {
        logger.info("Article create request received: {}", message);
        Article article = new Article();
        article.setKey(message.getKey())
                .setTitle(message.getTitle())
                .setDescription(message.getDescription())
                .setAuthor(getUser().getName())
                .setTimestamp(BlinkTime.getCurrentTimeMillis());
        articleHelper.saveEntity(article);
        adminService.sendReply(new CreateArticleResponseMessage(message.getKey(), true, "Success"));
    }

    private void handleArticleDelete(ArticleDeleteMessage message) throws Exception {
        boolean success = articleHelper.deleteEntity(message.getKey());
        adminService.sendReply(new ArticleDeleteResponeMessage(message.getKey(), success));
        logger.info("Article delete status [sucess={} key={}]", success, message.getKey());
    }
}
