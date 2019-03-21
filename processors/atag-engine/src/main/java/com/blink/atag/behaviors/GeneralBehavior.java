package com.blink.atag.behaviors;

import com.blink.atag.BehaviorModifier;

public class GeneralBehavior extends BehaviorModifier {

    @Override
    public void action(final String line) {
        builderDelegate.getActiveBuilder().addLine(line);
    }
}