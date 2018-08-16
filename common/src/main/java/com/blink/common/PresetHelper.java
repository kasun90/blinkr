package com.blink.common;

import com.blink.core.database.DBService;
import com.blink.core.database.Filter;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.database.SortCriteria;
import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.file.FileService;
import com.blink.core.service.BaseService;
import com.blink.shared.common.File;
import com.blink.shared.common.Photo;
import com.blink.shared.common.Preset;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PresetHelper extends CommonHelper<Preset> {
    private final String beforeImage = "before.jpg";
    private final String afterImage = "after.jpg";

    public PresetHelper(BaseService service) {
        super(service, "presets",
                service.getContext().getFileService().newFileURI("presets").build(),
                Preset.class);
    }

    @Override
    public Preset fillEntity(Preset preset) throws Exception {
        String templateFile = getTemplateFileURI(preset.getKey());

        if (fileService.exists(templateFile))
            preset.setTemplateFile(new File(templateFile, fileService.getURL(templateFile).toString()));

        String beforeImageFile = getBeforeImageURI(preset.getKey());
        if (fileService.exists(beforeImageFile))
            preset.setBeforeImage(new Photo(beforeImageFile, fileService.getURL(beforeImageFile).toString()));

        String afterImageFile = getAfterImageURI(preset.getKey());
        if (fileService.exists(afterImageFile))
            preset.setAfterImage(new Photo(afterImageFile, fileService.getURL(afterImageFile).toString()));
        return preset;
    }

    @Override
    public void cleanupEntityGarbage(String key) throws Exception {
        fileService.delete(getTemplateFileURI(key));
        fileService.delete(getBeforeImageURI(key));
        fileService.delete(getAfterImageURI(key));
    }

    public boolean saveTemplateFile(String key, String fileContent) throws Exception {
        return saveFile(key, fileContent, getTemplateFileURI(key));
    }

    public boolean saveBeforeImage(String key, String fileContent) throws Exception {
        return saveFile(key, fileContent, getBeforeImageURI(key));
    }

    public boolean saveAfterImage(String key, String fileContent) throws Exception {
        return saveFile(key, fileContent, getAfterImageURI(key));
    }

    private boolean saveFile(String key, String fileContent, String path) throws Exception {
        Preset entity = getEntity(key);

        if (entity == null) {
            service.error("No preset found [key={}]", key);
            return false;
        }
        uploadFile(path, fileContent);
        return true;
    }

    private String getTemplateFileURI(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource(key + ".zip").build();
    }

    private String getBeforeImageURI(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource(beforeImage).build();
    }

    private String getAfterImageURI(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource(afterImage).build();
    }
}
