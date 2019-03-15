package com.blink.core.transport.blinkr;

import com.blink.core.transport.Bus;
import com.blink.core.transport.BusFactory;

public class BlinkrBusFactory implements BusFactory {

    @Override
    public Bus create() {
        return new BlinkrEventBus();
    }
}