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
                builder.addField(element.getTextContent(), convertField(element.getAttribute("type")));
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

    private String convertField(String attrValue) {
        StringBuilder builder = new StringBuilder();
        if (attrValue.startsWith("List")) {
            return builder.append("List<").append(attrValue.split("::")[1]).append(">").toString();
        } else if (attrValue.startsWith("Map")) {
            String[] split = attrValue.split("::");
            if (split.length == 2)
                builder.append("Map<String, ").append(split[1]).append(">");
            else
                builder.append("Map<").append(split[1]).append(", ").append(split[2]).append(">");
            return builder.toString();
        } else
            return attrValue;
    }

    public Context.ContextBuilder getBuilder() {
        return builder;
    }
}
