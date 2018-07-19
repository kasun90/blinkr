package com.blink.core.file;

import com.blink.core.file.local.LocalFileService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
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

    @Test
    public void URLTest() throws Exception {
        URL url = fileService.getURL("articles/myfirst.txt");
        System.out.println(url.toString());
    }

    @Test
    public void existsTest() throws Exception {
        String articles = fileService.newFileURI().appendResource("articles").appendResource("myfirst2.txt").build();
        System.out.println(fileService.exists(articles));
    }

}