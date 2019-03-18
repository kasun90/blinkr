package com.blink.atag;

import com.blink.atag.tags.SimpleATag;

public interface BehaviorController {
    Behavior getBehavior(String line);
    SimpleATag conclude();
}
