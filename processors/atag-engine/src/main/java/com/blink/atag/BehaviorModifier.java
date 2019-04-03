package com.blink.atag;


import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.builders.SimpleATagBuilder;
import com.blink.core.exception.BlinkRuntimeException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

public abstract class BehaviorModifier implements Behavior {
    protected List<SimpleATagBuilder> builders;
    protected BuilderDelegate builderDelegate;
    protected SimpleATag currentOutput;

    void setBuilders(List<SimpleATagBuilder> builders) {
        this.builders = builders;
    }

    void setBuilderDelegate(BuilderDelegate builderDelegate) {
        this.builderDelegate = builderDelegate;
    }

    protected void checkSingleBuilderPresence(Class<? extends Behavior> behaviorClass) {
        if (builders == null)
            throw new BlinkRuntimeException(MessageFormat.format("{0} should have an builder associated",
                    behaviorClass.getName()));
        else if (builders.size() != 1)
            throw new BlinkRuntimeException(MessageFormat.format("{0} only accepts single builder",
                    behaviorClass.getName()));
    }

    @Override
    public Optional<SimpleATag> output() {
        return Optional.ofNullable(currentOutput);
    }

    @Override
    public void startOver() {
        currentOutput = null;
    }
}
