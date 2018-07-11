package com.blink.shared;

import java.util.*;
import java.util.function.Consumer;

public final class Context {
    private Type type;
    private String className;
    private String fileName;
    private String packageName;
    private Set<String> imports;
    private String extendClassName;
    private Map<String, String> fields;
    private List<String> cases;

    public Context(ContextBuilder builder) {
        this.type = builder.type;
        this.className = builder.className;
        this.fileName = builder.fileName;
        this.packageName = builder.packageName;
        this.imports = builder.imports;
        this.extendClassName = builder.extendClassName;
        this.fields = builder.fields;
        this.cases = builder.cases;
        refineContext();
    }

    private void refineContext() {
        this.imports.add("com.blink.utilities.BlinkJSON");
        this.fields.values().forEach(type -> {
            if (type.contains("List"))
                this.imports.add("java.util.List");

            if (type.contains("Map"))
                this.imports.add("java.util.Map");

        });
    }

    public Type getType() {
        return type;
    }

    public String getClassName() {
        return className;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public String getExtendClassName() {
        return extendClassName;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public List<String> getCases() {
        return cases;
    }

    public static class ContextBuilder {
        private Type type;
        private String className;
        private String fileName;
        private String packageName;
        private Set<String> imports;
        private String extendClassName;
        private Map<String, String> fields;
        private List<String> cases;

        public ContextBuilder() {
            type = Type.CLASS;
            imports = new HashSet<>();
            fields = new LinkedHashMap<>();
            cases = new LinkedList<>();
        }

        public ContextBuilder setType(Type type) {
            this.type = type;
            return this;
        }

        public ContextBuilder setClassName(String className) {
            this.className = className;
            return this;
        }

        public ContextBuilder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ContextBuilder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public ContextBuilder addImport(String importName) {
            imports.add(importName);
            return this;
        }

        public ContextBuilder setExtendClassName(String extendClassName) {
            this.extendClassName = extendClassName;
            return this;
        }

        public ContextBuilder addField(String name, String type) {
            fields.put(name, type);
            return this;
        }

        public ContextBuilder addCase(String caseName) {
            cases.add(caseName);
            return this;
        }

        public Context build() {
            return new Context(this);
        }
    }

    public enum Type {
        CLASS,
        ENUM
    }
}
