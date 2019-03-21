package com.blink.atag.behaviors;

import com.blink.atag.BehaviorModifier;
import com.blink.atag.tags.builders.SimpleATagBuilder;

public class SpecialStatefulBehavior extends BehaviorModifier {

    @Override
    public void action(final String line) throws Exception {
        this.checkSingleBuilderPresence(this.getClass());

        SimpleATagBuilder builder = builders.get(0);

        if (!builder.isBuilding())
            builder.initNew();
        builder.addLine(line);
    }
}