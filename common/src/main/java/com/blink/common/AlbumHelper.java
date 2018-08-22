package com.blink.common;

import com.blink.core.service.Context;
import com.blink.shared.common.Album;
import com.blink.shared.common.Photo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlbumHelper extends CommonHelper<Album> {
    private String coverPhotoName = "cover.jpg";

    public AlbumHelper(Context context) {
        super(context, "albums",
                context.getFileService().newFileURI("albums").build(), Album.class);
    }

    @Override
    public Album fillEntity(Album album) throws Exception {
        String photoPath = fileService.newFileURI(entityBase).appendResource(album.getKey()).appendResource("photos").build();


        List<Photo> photos = fileService.listFilePaths(photoPath).stream().map(path -> {
            try {
                return new Photo(path, fileService.getURL(path).toString());
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        album.setPhotos(photos);

        String coverPath = fileService.newFileURI(entityBase).appendResource(album.getKey()).appendResource("cover")
                .appendResource(coverPhotoName).build();

        if (fileService.exists(coverPath))
            album.setCover(new Photo(coverPath, fileService.getURL(coverPath).toString()));
        return album;
    }

    @Override
    public void cleanupEntityGarbage(String key) throws Exception {
        String photoPath = fileService.newFileURI(entityBase).appendResource(key).appendResource("photos").build();
        fileService.listFilePaths(photoPath).forEach(path -> {
            try {
                fileService.delete(path);
            } catch (Exception e) {
                logger.exception("Exception while deleting photo", e);
            }
        });

        String coverPath = fileService.newFileURI(entityBase).appendResource(key).appendResource("cover").build();
        fileService.listFilePaths(coverPath).forEach(path -> {
            try {
                fileService.delete(path);
            } catch (Exception e) {
                logger.exception("Exception while deleting photo", e);
            }
        });
    }

    public boolean savePhoto(String key, String fileContent) throws Exception {
        Album album = getEntity(key);

        if (album == null) {
            logger.error("Album is not available to add the photo [key={}]", key);
            return false;
        }

        saveEntity(album.setCount(album.getCount() + 1));
        String path = fileService.newFileURI(entityBase).appendResource(key).appendResource("photos")
                .appendResource(album.getCount() + ".jpg").build();

        uploadFile(path, fileContent);
        return true;
    }

    public boolean saveCover(String key, String fileContent) throws Exception {
        Album album = getEntity(key);

        if (album == null) {
            logger.error("Album is not available to add the cover [key={}]", key);
            return false;
        }

        String coverPath = fileService.newFileURI(entityBase).appendResource(key).appendResource("cover")
                .appendResource(coverPhotoName).build();
        uploadFile(coverPath, fileContent);
        return true;
    }


    public static class AlbumBuilder {
        private String title;
        private String key;
        private String description;
        private int count;
        private List<Photo> photos;
        private Photo cover;
        private long timestamp;

        public AlbumBuilder() {
            photos = new LinkedList<>();
        }

        public AlbumBuilder(Album withAblum) {
            this.title = withAblum.getTitle();
            this.key = withAblum.getKey();
            this.description = withAblum.getDescription();
            this.count = withAblum.getCount();
            this.photos = withAblum.getPhotos();
            this.cover = withAblum.getCover();
            this.timestamp = withAblum.getTimestamp();
        }

        public AlbumBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public AlbumBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public AlbumBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public AlbumBuilder setCount(int count) {
            this.count = count;
            return this;
        }

        public AlbumBuilder setPhotos(List<Photo> photos) {
            this.photos = photos;
            return this;
        }

        public AlbumBuilder setCover(Photo cover) {
            this.cover = cover;
            return this;
        }

        public AlbumBuilder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Album build() {
            return new Album(title, timestamp, key, description, count, photos, cover);
        }
    }

}
