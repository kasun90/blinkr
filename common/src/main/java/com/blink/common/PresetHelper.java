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

public class PresetHelper {
    private BaseService service;
    private DBService presetDB;
    private String presetBase;
    private final String beforeImage = "before.jpg";
    private final String afterImage = "after.jpg";
    private FileService fileService;

    public PresetHelper(BaseService service) {
        this.service = service;
        this.presetDB = service.getContext().getDbServiceFactory().ofCollection("presets");
        this.presetBase = service.getContext().getFileService().newFileURI("presets").build();
        this.fileService = service.getContext().getFileService();
    }

    public boolean isKeyAvailable(String key) throws Exception {
        if (key == null)
            throw new BlinkRuntimeException("Preset key cannot be null");
        return getPreset(key) == null;
    }

    private Preset getPreset(String key) throws Exception {
        return presetDB.find(new SimpleDBObject().append("key", key), Preset.class).first();
    }

    public void savePreset(Preset preset) throws Exception {
        if (preset.getKey() == null) {
            service.error("Preset key cannot be null");
            throw new BlinkRuntimeException("Preset key cannot be null");
        }

        presetDB.insertOrUpdate(new SimpleDBObject().append("key", preset.getKey()), preset);
    }

    public int getPresetCount() throws Exception {
        return Math.toIntExact(presetDB.count(Preset.class));
    }

    public List<Preset> getPresets(long timestamp, boolean less, int limit) throws Exception {
        List<Preset> presets = new LinkedList<>();
        int current = 0;
        if (timestamp == 0L) {
            Iterator<Preset> iterator = presetDB.findAll(Preset.class, SortCriteria.descending("timestamp")).iterator();
            while (iterator.hasNext() && current < limit) {
                presets.add(fillPreset(iterator.next()));
                current++;
            }
        } else {
            SimpleDBObject toFind = new SimpleDBObject();
            if (less)
                toFind.append("timestamp", timestamp, Filter.LT);
            else
                toFind.append("timestamp", timestamp, Filter.GT);

            Iterator<Preset> iterator = presetDB.find(toFind, Preset.class).iterator();
            while (iterator.hasNext() && current < limit) {
                presets.add(fillPreset(iterator.next()));
                current++;
            }
        }

        return presets;
    }

    private Preset fillPreset(Preset preset) throws Exception {
        String templateFile = fileService.newFileURI(presetBase).appendResource(preset.getKey()).appendResource(preset.getKey() + ".zip").build();

        if (fileService.exists(templateFile))
            preset.setTemplateFile(new File(templateFile, fileService.getURL(templateFile).toString()));

        String beforeImageFile = fileService.newFileURI(presetBase).appendResource(preset.getKey()).appendResource(beforeImage).build();
        if (fileService.exists(beforeImageFile))
            preset.setBeforeImage(new Photo(beforeImageFile, fileService.getURL(beforeImageFile).toString()));

        String afterImageFile = fileService.newFileURI(presetBase).appendResource(preset.getKey()).appendResource(afterImage).build();
        if (fileService.exists(afterImageFile))
            preset.setAfterImage(new Photo(afterImageFile, fileService.getURL(afterImageFile).toString()));
        return preset;
    }



    private void uploadFile(String path, String fileContent) throws Exception {
        String localFile = fileService.createLocalFile(path);

        String substring = fileContent.substring(fileContent.indexOf(",") + 1);
        byte[] decode = Base64.getDecoder().decode(substring);
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(localFile))) {
            output.write(decode);
        }

        fileService.upload(path);
        service.info("File uploaded {}", path);
    }



}
