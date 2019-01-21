package com.blink.shared;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

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

        if (doc.getDocumentElement().hasAttribute("extends")) {
            builder.setExtendClassName(doc.getDocumentElement().getAttribute("extends"));
            fillExtendFields(builder);
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

    private void fillExtendFields(Context.ContextBuilder builder) throws Exception {
        String extendFullClassName = null;

        for (String anImport : builder.getImports()) {
            if (anImport.contains(builder.getExtendClassName())) {
                extendFullClassName = anImport;
                break;
            }
        }

        if (extendFullClassName == null)
            extendFullClassName = builder.getPackageName() + "." + builder.getExtendClassName();

        Class<?> aClass = Class.forName(extendFullClassName);
        fillExtendedFields(aClass, builder);
    }

    private void fillExtendedFields(Class<?> aClass, Context.ContextBuilder builder) {
        if (aClass.getSuperclass() != null)
            fillExtendedFields(aClass.getSuperclass(), builder);

        for (Field field : aClass.getDeclaredFields()) {
            String name = field.getName();
            String type = convertExtendField(field.getType().getSimpleName(), field.getGenericType().getTypeName());
            builder.addExtendField(name, type);
        }
    }

    private String convertExtendField(String simpleName, String genericName) {
        StringBuilder builder = new StringBuilder();
        switch (simpleName) {
            case "List":
                String genericClassFullName = genericName.substring(genericName.indexOf('<'), genericName.indexOf('>'));
                return builder.append(simpleName)
                        .append("<")
                        .append(getSimpleNameFromFQN(genericClassFullName))
                        .append(">").toString();
            case "Map":
                String genericClassName1 = genericName.substring(genericName.indexOf('<'), genericName.indexOf(',')).trim();
                String genericClassName2 = genericName.substring(genericName.indexOf(','), genericName.indexOf('>')).trim();
                return builder.append(simpleName)
                        .append("<")
                        .append(getSimpleNameFromFQN(genericClassName1))
                        .append(", ")
                        .append(getSimpleNameFromFQN(genericClassName2))
                        .append(">").toString();
            default:
                return simpleName;
        }
    }

    private String getSimpleNameFromFQN(String fqn) {
        return fqn.substring(fqn.lastIndexOf('.') + 1);
    }

    public Context.ContextBuilder getBuilder() {
        return builder;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        //com.blink.shared.client.ClientRequestMessage
        //com.blink.shared.client.album.AlbumsResponseMessage
        Class<?> aClass = Class.forName("com.blink.shared.SubSample");

        XMLParser parser = new XMLParser(null, null);
        List<String> fields = new LinkedList<>();
        parser.listFields(aClass, fields);

        for (String field : fields) {
            System.out.println(field);
        }
    }

    private void listFields(Class<?> aClass, List<String> fields) {
        if (aClass.getSuperclass() != null)
            listFields(aClass.getSuperclass(), fields);

        for (Field field : aClass.getDeclaredFields()) {
            fields.add(field.getName());
        }
    }
}
