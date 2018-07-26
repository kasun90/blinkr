package com.blink.core.file.local;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.file.FileURI;
import com.blink.core.file.TemporaryFileService;
import com.blink.core.service.Configuration;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LocalFileService extends TemporaryFileService {

    private String rootDir;
    private String domain;
    private String clientPort;

    public LocalFileService(String tmpDir, String rootDir) {
        super(tmpDir);
        this.rootDir = rootDir;
        this.domain = "localhost";
        this.clientPort = "5000";
    }

    public LocalFileService(Configuration configuration) {
        super(configuration);
        this.rootDir = configuration.getValue("staticFilesRoot");
        this.domain = configuration.getValue("domain");
        this.clientPort = configuration.getValue("clientPort");
    }

    @Override
    public boolean exists(String path) {
        return new File(rootDir + path).exists();
    }

    @Override
    public void upload(String path) throws Exception {
        upload(path, true);
    }

    @Override
    public void upload(String path, boolean deleteLocal) throws Exception {
        File localFile = new File(getLocalFile(path));
        File staticFile = new File(rootDir + path);
        staticFile.getParentFile().mkdirs();
        Files.copy(localFile.toPath(), staticFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        if (deleteLocal)
            localFile.delete();
    }

    @Override
    public void delete(String path) throws Exception {
        File staticFile = new File(rootDir + path);
        if (!staticFile.exists())
            return;
        Files.delete(staticFile.toPath());
    }

    @Override
    public List<String> listFilePaths(String path) throws Exception {
        Path filesPath = Paths.get(rootDir + path);
        if (!filesPath.toFile().exists())
            return new LinkedList<>();
        return Files.walk(filesPath)
                .filter(path1 -> !path1.toFile().isDirectory())
                .map(origin -> Paths.get(rootDir).relativize(origin).toString())
                .collect(Collectors.toList());
    }

    @Override
    public String download(String path) throws Exception {
        Path localPath = Paths.get(createLocalFile(path));
        Path remotePath = Paths.get(rootDir + path);
        if (!remotePath.toFile().exists())
            throw new BlinkRuntimeException("Remote file(" + remotePath.toString() + ") not found");
        Files.copy(remotePath, localPath, StandardCopyOption.REPLACE_EXISTING);
        return localPath.toAbsolutePath().toString();
    }

    @Override
    public URL getURL(String path) throws Exception {
        return new URL("http", domain, Integer.parseInt(clientPort), "/" + rootDir + path);
    }

    @Override
    public FileURI newFileURI() {
        return new LocalFileURI();
    }

    @Override
    public FileURI newFileURI(String base) {
        return new LocalFileURI(base);
    }
}
