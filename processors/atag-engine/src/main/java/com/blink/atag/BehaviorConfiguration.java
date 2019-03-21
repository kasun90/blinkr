package com.blink.atag;

import com.blink.atag.tags.*;
import com.blink.atag.tags.builders.SimpleATagBuilder;

final class BehaviorConfiguration {
    BehaviorRegistry registry;
    static final String BREAK_LINE = "[BREAK]";

    BehaviorConfiguration(BehaviorRegistry registry) {
        this.registry = registry;
    }

    BehaviorRegistry getRegistry() {
        return registry;
    }

    void initialize() {
        registry.register(line -> line.startsWith("#"), SingleLineBehavior.class, Header.class);
        registry.register(line -> line.isEmpty() || line.startsWith(BREAK_LINE),
                BreakTagBehavior.class, List.class, OrderedList.class, Paragraph.class);
        registry.register(line -> line.startsWith("!!"), BoundedBehavior.class, Note.class);
        registry.register(line -> line.startsWith("!("), SingleLineBehavior.class, Image.class);
        registry.register(line -> line.startsWith("**"), SpecialStatefulBehavior.class, List.class);
        registry.register(line -> line.startsWith("*@"), SpecialStatefulBehavior.class, OrderedList.class);
        registry.register(line -> line.startsWith("```"), BoundedBehavior.class, Terminal.class);
        registry.register(line -> line.startsWith("``"), BoundedBehavior.class, Code.class);
        registry.register(line -> line.startsWith("[gist]"), SingleLineBehavior.class, Gist.class);
    }

    class SingleLineBehavior extends BehaviorModifier {

        @Override
        public void action(final String line) {
            this.checkSingleBuilderPresence(this.getClass());

            SimpleATagBuilder builder = builders.get(0);
            builder.addLine(line);
            currentOutput = builder.build();
            builder.reset();
        }
    }

    class BreakTagBehavior extends BehaviorModifier {
        @Override
        public void action(final String line) {
            for (SimpleATagBuilder builder : builders) {
                if (builder.isBuilding()) {
                    currentOutput = builder.build();
                    builder.reset();
                    break;
                }
            }
        }
    }

    class BoundedBehavior extends BehaviorModifier {

        @Override
        public void action(final String line) throws Exception {
            this.checkSingleBuilderPresence(this.getClass());

            SimpleATagBuilder builder = builders.get(0);

            if (builder.isBuilding()) {
                currentOutput = builder.build();
                builder.reset();
                builderDelegate.restoreDefaultActiveBuilder();
            } else
                builderDelegate.setActiveBuilder(builder);
        }
    }

    class SpecialStatefulBehavior extends BehaviorModifier {

        @Override
        public void action(final String line) throws Exception {
            this.checkSingleBuilderPresence(this.getClass());

            SimpleATagBuilder builder = builders.get(0);

            if (!builder.isBuilding())
                builder.initNew();
            builder.addLine(line);
        }
    }

    class GeneralBehavior extends BehaviorModifier {

        @Override
        public void action(final String line) throws Exception {
            builderDelegate.getActiveBuilder().addLine(line);
        }
    }
}
