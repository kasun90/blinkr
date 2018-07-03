package com.blink.shared;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;


public class SharedMessageGenerator {

    private String resourcesDir = "src/main/resources/";

    public void start() throws Exception {
        List<File> files = new LinkedList<>();
        listFiles(resourcesDir, files);
        files.forEach(this::processSingleFile);
    }

    private void listFiles(String directoryName, Collection<File> files) {
        File[] fList = new File(directoryName).listFiles();

        assert fList != null;
        for (File file : fList) {
            if (file.isDirectory())
                listFiles(file.getPath(), files);
            else
                files.add(file);
        }
    }

    private void processSingleFile(File file) {
        try {
            Context.ContextBuilder builder = new Context.ContextBuilder();
            String dotPath = file.getPath().replaceAll(Matcher.quoteReplacement(FileSystems.getDefault().getSeparator()), ".");
            dotPath = dotPath.replaceAll(resourcesDir.replaceAll(Matcher.quoteReplacement("/"), "."), "").replaceAll(".xml", "");

            StringBuilder fileNameBuilder = new StringBuilder();
            fileNameBuilder.append("src/main/java/").append(dotPath.replaceAll("\\.", Matcher.quoteReplacement("/")))
                    .append(".java");
            builder.setFileName(fileNameBuilder.toString())
                    .setPackageName(dotPath.substring(0, dotPath.lastIndexOf(".")))
                    .setClassName(dotPath.substring(dotPath.lastIndexOf(".") + 1, dotPath.length()));
            new XMLParser(file, builder).parse();
            ClassWriter classWriter = new ClassWriter(builder.build());
            classWriter.start();
            classWriter.close();
            System.out.println("prcessed " + file.getPath());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        new SharedMessageGenerator().start();
    }
}
