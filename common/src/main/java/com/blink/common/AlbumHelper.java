package com.blink.common;

import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.common.Album;
import com.blink.shared.common.Photo;

import java.util.LinkedList;
import java.util.List;

public class AlbumHelper {
    private BaseService service;
    private DBService albumDB;

    public AlbumHelper(BaseService service) {
        this.service = service;
        this.albumDB = service.getContext().getDbServiceFactory().ofCollection("albums");
    }

    public boolean isKeyAvailable(String key) throws Exception {
        if (key == null)
            throw new BlinkRuntimeException("Album key cannot be null");
        Album album = albumDB.find(new SimpleDBObject().append("key", key), Album.class).first();
        return album == null;
    }

    public void saveAlbum(Album album) throws Exception {
        if (album.getKey() == null) {
            service.error("Album key cannot be null");
            throw new BlinkRuntimeException("Album key cannot be null");
        }

        albumDB.insertOrUpdate(new SimpleDBObject().append("key", album.getKey()), album);
    }

    public static class AlbumBuilder {
        private String title;
        private String key;
        private String description;
        private int count;
        private List<Photo> photos;
        private Photo cover;

        public AlbumBuilder() {
            photos = new LinkedList<>();
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

        public void setCover(Photo cover) {
            this.cover = cover;
        }

        public Album build() {
            return new Album(title, key, description, count, photos, cover);
        }
    }

}
