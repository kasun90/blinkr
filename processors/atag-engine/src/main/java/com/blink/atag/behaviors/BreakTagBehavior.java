package com.blink.atag.behaviors;

import com.blink.atag.Behavior;
import com.blink.atag.BehaviorModifier;
import com.blink.atag.tags.builders.SimpleATagBuilder;

public class BreakTagBehavior extends BehaviorModifier {
    @Override
    public Behavior action(final String line) {
        for (SimpleATagBuilder builder : builders) {
            if (builder.isBuilding()) {
                currentOutput = builder.build();
                builder.reset();
                break;
            }
        }
        return this;
    }
}