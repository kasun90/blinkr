package com.blink.shared;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {

    private File file;
    private Context.ContextBuilder builder;

    public XMLParser(File file, Context.ContextBuilder builder) {
        this.file = file;
        this.builder = builder;

    }

    public void parse() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(file);
        doc.normalizeDocument();

        if (doc.getDocumentElement().hasAttribute("extends")) {
            builder.setExtendClassName(doc.getDocumentElement().getAttribute("extends"));
        }

        String type = doc.getDocumentElement().getAttribute("type");
        if (type != null) {
            switch (type.toLowerCase()) {
                case "enum":
                    builder.setType(Context.Type.ENUM);
                    break;
                default:
                    break;
            }
        }

        NodeList importList = doc.getElementsByTagName("import");

        if (importList != null) {
            for (int i = 0; i < importList.getLength(); i++) {
                builder.addImport(importList.item(i).getTextContent());
            }
        }

        NodeList fieldList = doc.getElementsByTagName("field");

        if (fieldList != null) {
            for (int i = 0; i < fieldList.getLength(); i++) {
                Element element = Element.class.cast(fieldList.item(i));
                builder.addField(element.getTextContent(), element.getAttribute("type"));
            }
        }

        NodeList caseList = doc.getElementsByTagName("case");

        if (caseList != null) {
            for (int i = 0; i < caseList.getLength(); i++) {
                Element element = Element.class.cast(caseList.item(i));
                builder.addCase(element.getTextContent());
            }
        }
    }

    public Context.ContextBuilder getBuilder() {
        return builder;
    }
}
