package com.blink.atag;

import com.blink.atag.tags.SimpleATag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class BehaviorRegistry {

    private final List<BehaviorRegistryEntry> entries = new ArrayList<>();

    @SafeVarargs
    final void register(Rule rule, Class<Behavior> behaviorClass, Class<? extends SimpleATag>... tags) {
        entries.add(new BehaviorRegistryEntry(rule, behaviorClass, tags));
    }




    private class BehaviorRegistryEntry {
        private Rule rule;
        private Class<Behavior> behaviorClass;
        private List<Class<? extends SimpleATag>> associatedTags;

        @SafeVarargs
        BehaviorRegistryEntry(Rule rule, Class<Behavior> behaviorClass, Class<? extends SimpleATag>... tags) {
            this.rule = rule;
            this.behaviorClass = behaviorClass;
            this.associatedTags = Arrays.asList(tags);
        }
    }
}
