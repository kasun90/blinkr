package com.blink.atag;

import com.blink.atag.tags.SimpleATag;

import java.util.Optional;

public interface Behavior {
    Behavior action(final String line) throws Exception;
    Optional<SimpleATag> output();
    void startOver();
}
