package com.blink.core.transport.blinkr;

import com.blink.core.transport.Bus;
import xyz.justblink.eventbus.AsyncEventBus;
import xyz.justblink.eventbus.EventBus;

import java.util.concurrent.Executors;

public class BlinkrEventBus implements Bus {

    private final EventBus eventBus;

    BlinkrEventBus() {
        eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
    }

    @Override
    public void register(Object object) {
        eventBus.register(object);
    }

    @Override
    public void post(Object object) {
        eventBus.post(object);
    }

    @Override
    public void unregister(Object object) {
        eventBus.unregister(object);
    }
}
