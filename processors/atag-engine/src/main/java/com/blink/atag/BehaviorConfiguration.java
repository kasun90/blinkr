package com.blink.atag;

import com.blink.atag.behaviors.BoundedBehavior;
import com.blink.atag.behaviors.BreakTagBehavior;
import com.blink.atag.behaviors.SingleLineBehavior;
import com.blink.atag.behaviors.SpecialStatefulBehavior;
import com.blink.atag.tags.*;

final public class BehaviorConfiguration {
    static final String BREAK_LINE = "[BREAK]";
    private BehaviorRegistry registry;

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
}
