package com.blink.core.file;

import java.io.File;
import java.net.URL;
import java.util.List;

public interface FileService {
    String createLocalFile(String path) throws Exception;
    String getLocalFile(String path) throws Exception;
    void upload(String path) throws Exception;
    void upload(String path, boolean deleteLocal) throws Exception;
    void delete(String path) throws Exception;
    List<String> listFilePaths(String path) throws Exception;
    List<File> listFiles(String path) throws Exception;
    String download(String path) throws Exception;
    URL getURL(String path) throws Exception;
}
