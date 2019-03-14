package com.blink.core.transport.blinkr;

import com.blink.core.transport.AbstractBusService;
import com.blink.core.transport.Bus;
import com.blink.core.transport.BusFactory;

public class BlinkrBusService extends AbstractBusService {

    private final Bus defaultBus;

    public BlinkrBusService(BusFactory factory) {
        super(factory);
        defaultBus = factory.create();
    }

    @Override
    public Bus getDefault() {
        return defaultBus;
    }

    @Override
    public Bus createNew() {
        return factory.create();
    }
}
