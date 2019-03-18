package com.blink.atag;

import com.blink.atag.tags.*;
import com.blink.atag.tags.builders.*;
import com.blink.core.exception.BlinkRuntimeException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final class BuilderRegistry {
    private static Map<Class<? extends SimpleATag>, Class<? extends SimpleATagBuilder>> builderMap = new HashMap<>();

    static {
        builderMap.put(Code.class, CodeBuilder.class);
        builderMap.put(Gist.class, GistBuilder.class);
        builderMap.put(Header.class, HeaderBuilder.class);
        builderMap.put(Image.class, ImageBuilder.class);
        builderMap.put(List.class, UnorderedListBuilder.class);
        builderMap.put(Note.class, NoteBuilder.class);
        builderMap.put(OrderedList.class, OrderedListBuilder.class);
        builderMap.put(Paragraph.class, ParagraphBuilder.class);
        builderMap.put(RichText.class, RichTextBuilder.class);
        builderMap.put(Terminal.class, TerminalBuilder.class);
    }

    SimpleATagBuilder get(Class<? extends SimpleATag> tag) throws Exception {
        Class<? extends SimpleATagBuilder> aClass = builderMap.get(tag);
        if (aClass == null)
            throw new BlinkRuntimeException(MessageFormat.format("No builder found for this tag: {0}",
                    tag.getName()));
        return (SimpleATagBuilder) aClass.getDeclaredConstructors()[0].newInstance();
    }

    @SafeVarargs
    final java.util.List<SimpleATagBuilder> get(Class<? extends SimpleATag>... tags) throws Exception {
        java.util.List<SimpleATagBuilder> builders = new ArrayList<>();
        for (Class<? extends SimpleATag> tag : tags) {
            builders.add(get(tag));
        }
        return builders;
    }

}
