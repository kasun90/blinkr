package com.blink.atag.behaviors;

import com.blink.atag.BehaviorModifier;
import com.blink.atag.tags.builders.SimpleATagBuilder;

public class SingleLineBehavior extends BehaviorModifier {

    @Override
    public void action(final String line) {
        this.checkSingleBuilderPresence(this.getClass());

        SimpleATagBuilder builder = builders.get(0);
        builder.addLine(line);
        currentOutput = builder.build();
        builder.reset();
    }
}