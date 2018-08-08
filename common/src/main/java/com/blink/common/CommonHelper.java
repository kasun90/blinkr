package com.blink.common;

import com.blink.core.database.DBService;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.file.FileService;
import com.blink.core.service.BaseService;
import com.blink.shared.common.Entity;

public abstract class CommonHelper<T extends Entity> {
    BaseService service;
    FileService fileService;
    DBService entityDB;
    String entityBase;
    private Class<T> entityType;

    public CommonHelper(BaseService service, String entityCollectionName, String entityBaseName, Class<T> entityType) {
        this.service = service;
        this.entityDB = service.getContext().getDbServiceFactory().ofCollection(entityCollectionName);
        this.fileService = service.getContext().getFileService();
        this.entityBase = service.getContext().getFileService().newFileURI(entityBaseName).build();
        this.entityType = entityType;
    }

    public boolean isKeyAvailable(String key) throws Exception {
        if (key == null)
            throw new BlinkRuntimeException("Key cannot be null");
        return getEntity(key) == null;
    }

    private T getEntity(String key) throws Exception {
        return entityDB.find(new SimpleDBObject().append("key", key), entityType).first();
    }




}
