package com.blink.atag.behaviors;

import com.blink.atag.Behavior;
import com.blink.atag.BehaviorModifier;
import com.blink.atag.tags.builders.SimpleATagBuilder;

public class BoundedBehavior extends BehaviorModifier {

    @Override
    public Behavior action(final String line) throws Exception {
        this.checkSingleBuilderPresence(this.getClass());

        SimpleATagBuilder builder = builders.get(0);

        if (builder.isBuilding()) {
            currentOutput = builder.build();
            builder.reset();
            builderDelegate.restoreDefaultActiveBuilder();
        } else
            builderDelegate.setActiveBuilder(builder);
        return this;
    }
}