package com.blink.atag.tags.builders;

import com.blink.atag.tags.Note;
import com.blink.atag.tags.SimpleATag;

public class NoteBuilder extends SimpleATagBuilder {

    private StringBuilder builder;

    public NoteBuilder() {
        builder = new StringBuilder();
    }

    @Override
    public void addLine(String line) {
        line = line.trim();

        if (builder.length() != 0)
            builder.append(" ");
        builder.append(line);
    }

    @Override
    public SimpleATag build() {
        return new Note(builder.toString());
    }

    @Override
    public boolean isBuilding() {
        return builder.length() != 0;
    }

    @Override
    public void reset() {
        builder.setLength(0);
    }
}
