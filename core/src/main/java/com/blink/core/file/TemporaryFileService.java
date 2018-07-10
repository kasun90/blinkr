package com.blink.core.file;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.Configuration;

import java.io.File;

public abstract class TemporaryFileService implements FileService {

    private String tmpDir;

    public TemporaryFileService(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public TemporaryFileService(Configuration configuration) {
        this.tmpDir = configuration.getValue("tmpDir", "tmp/");
    }

    @Override
    public String createLocalFile(String path) {
        File file = new File(tmpDir + path);
        file.getParentFile().mkdirs();
        return file.getAbsolutePath();
    }

    @Override
    public String getLocalFile(String path) {
        String fullPath = tmpDir + path;
        File file = new File(fullPath);
        if (!file.exists())
            throw new BlinkRuntimeException("Temporary file (" + fullPath+ ") not found");
        return file.getAbsolutePath();
    }
}
