package com.blink.common;

import com.blink.core.database.DBService;
import com.blink.core.database.Filter;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.database.SortCriteria;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.file.FileService;
import com.blink.core.service.BaseService;
import com.blink.shared.common.Album;
import com.blink.shared.common.Photo;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlbumHelper {
    private BaseService service;
    private DBService albumDB;
    private String albumBase;
    private FileService fileService;
    private String coverPhotoName = "cover.jpg";

    public AlbumHelper(BaseService service) {
        this.service = service;
        this.albumDB = service.getContext().getDbServiceFactory().ofCollection("albums");
        albumBase = service.getContext().getFileService().newFileURI("albums").build();
        fileService = service.getContext().getFileService();
    }

    public boolean isKeyAvailable(String key) throws Exception {
        if (key == null)
            throw new BlinkRuntimeException("Album key cannot be null");
        return getAlbum(key) == null;
    }

    public void saveAlbum(Album album) throws Exception {
        if (album.getKey() == null) {
            service.error("Album key cannot be null");
            throw new BlinkRuntimeException("Album key cannot be null");
        }

        albumDB.insertOrUpdate(new SimpleDBObject().append("key", album.getKey()), album);
    }

    public int getAlbumsCount() throws Exception {
        return Math.toIntExact(albumDB.count(Album.class));
    }

    public List<Album> getAlbums(long timestamp, boolean less, int limit) throws Exception {
        List<Album> albums = new LinkedList<>();
        int current = 0;
        if (timestamp == 0L) {
            Iterator<Album> iterator = albumDB.findAll(Album.class, SortCriteria.descending("timestamp")).iterator();
            while (iterator.hasNext() && current < limit) {
                albums.add(fillPhotos(iterator.next()));
                current++;
            }
        } else {
            SimpleDBObject toFind = new SimpleDBObject();
            if (less)
                toFind.append("timestamp", timestamp, Filter.LT);
            else
                toFind.append("timestamp", timestamp, Filter.GT);

            Iterator<Album> iterator = albumDB.find(toFind, Album.class).iterator();
            while (iterator.hasNext() && current < limit) {
                albums.add(fillPhotos(iterator.next()));
                current++;
            }
        }

        return albums;
    }

    private Album getAlbum(String key) throws Exception {
        return albumDB.find(new SimpleDBObject().append("key", key), Album.class).first();
    }

    private Album fillPhotos(Album album) throws Exception {
        String photoPath = fileService.newFileURI(albumBase).appendResource(album.getKey()).appendResource("photos").build();


        List<Photo> photos = fileService.listFilePaths(photoPath).stream().map(path -> {
            try {
                return new Photo(path, fileService.getURL(path).toString());
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        album.setPhotos(photos);

        String coverPath = fileService.newFileURI(albumBase).appendResource(album.getKey()).appendResource("cover")
                .appendResource(coverPhotoName).build();

        if (fileService.exists(coverPath))
            album.setCover(new Photo(coverPath, fileService.getURL(coverPath).toString()));
        return album;
    }

    public boolean savePhoto(String key, String fileContent) throws Exception {
        Album album = getAlbum(key);

        if (album == null) {
            service.error("Album is not available to add the photo [key={}]", key);
            return false;
        }

        saveAlbum(album.setCount(album.getCount() + 1));
        String path = fileService.newFileURI(albumBase).appendResource(key).appendResource("photos")
                .appendResource(album.getCount() + ".jpg").build();

        uploadFile(path, fileContent);
        return true;
    }

    public boolean saveCover(String key, String fileContent) throws Exception {
        Album album = getAlbum(key);

        if (album == null) {
            service.error("Album is not available to add the cover [key={}]", key);
            return false;
        }

        String coverPath = fileService.newFileURI(albumBase).appendResource(key).appendResource("cover")
                .appendResource(coverPhotoName).build();
        uploadFile(coverPath, fileContent);
        return true;
    }

    private void uploadFile(String path, String fileContent) throws Exception {
        String localFile = fileService.createLocalFile(path);

        String substring = fileContent.substring(fileContent.indexOf(",") + 1);
        byte[] decode = Base64.getDecoder().decode(substring);
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(localFile))) {
            output.write(decode);
        }

        fileService.upload(path);
        service.info("Photo uploaded {}", path);
    }

    public boolean deleteAlbum(String key) throws Exception {
        Album album = getAlbum(key);

        if (album == null) {
            service.error("No album to delete [key={}]", key);
            return true;
        }

        removeAlbumFiles(key);
        albumDB.delete(new SimpleDBObject().append("key", key), Album.class);
        return true;
    }

    private void removeAlbumFiles(String key) throws Exception {
        String photoPath = fileService.newFileURI(albumBase).appendResource(key).appendResource("photos").build();
        fileService.listFilePaths(photoPath).forEach(path -> {
            try {
                fileService.delete(path);
            } catch (Exception e) {
                service.exception("Exception while deleting photo", e);
            }
        });

        String coverPath = fileService.newFileURI(albumBase).appendResource(key).appendResource("cover").build();
        fileService.listFilePaths(coverPath).forEach(path -> {
            try {
                fileService.delete(path);
            } catch (Exception e) {
                service.exception("Exception while deleting photo", e);
            }
        });
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
            return new Album(title, key, description, count, photos, cover, timestamp);
        }
    }

}
