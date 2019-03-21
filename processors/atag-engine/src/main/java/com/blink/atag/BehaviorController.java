package com.blink.atag;

import com.blink.atag.tags.SimpleATag;

import java.util.Optional;

public interface BehaviorController {
    Behavior getBehavior(final String line) throws Exception;
    Optional<SimpleATag> conclude() throws Exception;
}
