package com.blink.core.transport;

public abstract class AbstractBusService implements BusService {
    protected BusFactory factory;

    public AbstractBusService(BusFactory factory) {
        this.factory = factory;
    }
}
