package com.blink.atag;

import com.blink.atag.behaviors.GeneralBehavior;
import com.blink.atag.tags.Paragraph;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.builders.SimpleATagBuilder;
import com.blink.core.exception.BlinkRuntimeException;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class ATagBehaviorController implements BehaviorController, BuilderDelegate {

    private SimpleATagBuilder activeBuilder;
    private BuilderRegistry builderRegistry;
    private BehaviorConfiguration configuration;
    private final Map<Class<? extends Behavior>, Behavior> behaviorCache = new HashMap<>();

    ATagBehaviorController(BuilderRegistry builderRegistry, BehaviorConfiguration configuration) throws Exception {
        this.builderRegistry = builderRegistry;
        this.configuration = configuration;
        configuration.initialize();
        restoreDefaultActiveBuilder();
    }

    @Override
    public Behavior getBehavior(final String line) throws Exception{
        Behavior behavior;
        Optional<BehaviorRegistry.BehaviorRegistryEntry> behaviorEntryOptional = configuration.getRegistry().getEntries().
                stream().filter(entry -> entry.getRule().evaluate(line)).findFirst();

        List<Class<? extends SimpleATag>> associatedTags = null;

        if (behaviorEntryOptional.isPresent()) {
            BehaviorRegistry.BehaviorRegistryEntry behaviorRegistryEntry = behaviorEntryOptional.get();
            behavior = createBehavior(behaviorRegistryEntry.getBehaviorClass());
            associatedTags = behaviorRegistryEntry.getAssociatedTags();
        } else
            behavior = createBehavior(GeneralBehavior.class);

        if (behavior instanceof BehaviorModifier) {
            BehaviorModifier behaviorModifier = (BehaviorModifier) behavior;
            behaviorModifier.setBuilderDelegate(this);
            if (associatedTags != null)
                behaviorModifier.setBuilders(builderRegistry.get(associatedTags));
        }

        return behavior;
    }

    private Behavior createBehavior(Class<? extends Behavior> behaviorClass) throws Exception {
        Behavior behavior = behaviorCache.get(behaviorClass);
        if (behavior != null) {
            behavior.startOver();
            return behavior;
        }

        Constructor<?> constructor;

        for (Constructor<?> behaviorClassConstructor : behaviorClass.getConstructors()) {
            if (behaviorClassConstructor.getParameterCount() == 0) {
                constructor = behaviorClassConstructor;
                Behavior newBehaviorInstance = (Behavior) constructor.newInstance();
                behaviorCache.put(behaviorClass, newBehaviorInstance);
                return newBehaviorInstance;
            }
        }

        throw new BlinkRuntimeException(MessageFormat.format("Could not create behavior for the class {0} " +
                        "No valid constructor found",
                behaviorClass.getName()));
    }

    @Override
    public Optional<SimpleATag> conclude() throws Exception {
        Behavior behavior = getBehavior(BehaviorConfiguration.BREAK_LINE);
        return behavior.action(BehaviorConfiguration.BREAK_LINE).output();
    }

    @Override
    public void setActiveBuilder(SimpleATagBuilder builder) {
        this.activeBuilder = builder;
    }

    @Override
    public SimpleATagBuilder getActiveBuilder() {
        if (activeBuilder == null)
            throw new BlinkRuntimeException("No active builder found. There must be an active builder");
        return activeBuilder;
    }

    @Override
    public void restoreDefaultActiveBuilder() throws Exception {
        this.activeBuilder = builderRegistry.get(Paragraph.class);
    }
}
