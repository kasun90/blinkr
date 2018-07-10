package com.blink.core.file;

import com.blink.core.file.local.LocalFileService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class FileServiceTest {
    FileService fileService;

    @Before
    public void initFileService() {
        fileService = new LocalFileService("tmp/", "static/files/");
    }

    @Test
    public void fileUploadTest() throws Exception {
        String relativePath = "articles/myfirst.txt";
        String localFile = fileService.createLocalFile(relativePath);

        try (PrintWriter writer = new PrintWriter(new FileWriter(localFile))) {
            writer.println("Second text");
        }

        fileService.upload(relativePath);
    }

    @Test
    public void listFilesTest() throws Exception {
        fileService.listFilePaths("articles").forEach(System.out::println);
    }

    @Test
    public void downloadTest() throws Exception {
        String download = fileService.download("articles/myfirst.txt");
        System.out.println(download);
    }

    @Test
    public void deleteTest() throws Exception {
        fileService.delete("articles/myfirst.txt");
    }



}