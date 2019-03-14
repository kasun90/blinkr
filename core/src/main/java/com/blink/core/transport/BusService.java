package com.blink.core.transport;

public interface BusService {
    Bus getDefault();
    Bus createNew();
}
