package com.blink.core.transport.google;

import com.blink.core.transport.Bus;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

public class GoogleEventBus implements Bus {
    private EventBus eventBus;

    public GoogleEventBus() {
        eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
    }

    @Override
    public Bus createNew() {
        return new GoogleEventBus();
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
