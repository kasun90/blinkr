package com.blink.common;

import com.blink.core.service.Context;
import com.blink.shared.common.File;
import com.blink.shared.common.Photo;
import com.blink.shared.common.Preset;

import java.util.List;

public class PresetHelper extends CommonHelper<Preset> {
    private final String beforeImage = "before.jpg";
    private final String afterImage = "after.jpg";

    public PresetHelper(Context context) {
        super(context, "presets",
                context.getFileService().newFileURI("presets").build(),
                Preset.class);
    }

    @Override
    public Preset fillEntity(Preset preset) throws Exception {
        List<String> templateFiles = fileService.listFilePaths(getTemplateFileURIBase(preset.getKey()));
        if (!templateFiles.isEmpty())
            preset.setTemplateFile(new File(templateFiles.get(0), fileService.getURL(templateFiles.get(0)).toString()));

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
        List<String> templates = fileService.listFilePaths(getTemplateFileURIBase(key));
        for (String template : templates) {
            fileService.delete(template);
        }
        fileService.delete(getBeforeImageURI(key));
        fileService.delete(getAfterImageURI(key));
    }

    public boolean saveTemplateFile(String key, String fileContent, String fileName) throws Exception {
        return saveFile(key, fileContent, getTemplateFileURI(key, fileName));
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
            logger.error("No preset found [key={}]", key);
            return false;
        }
        uploadFile(path, fileContent);
        return true;
    }

    private String getTemplateFileURI(String key, String fileName) {
        return fileService.newFileURI(getTemplateFileURIBase(key)).appendResource(fileName).build();
    }

    private String getTemplateFileURIBase(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource("template").build();
    }

    private String getBeforeImageURI(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource(beforeImage).build();
    }

    private String getAfterImageURI(String key) {
        return fileService.newFileURI(entityBase).appendResource(key).appendResource(afterImage).build();
    }
}
