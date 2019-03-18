package com.blink.atag;

import com.blink.atag.tags.SimpleATag;

interface Behavior {
    void action(String line);
    SimpleATag output();
}
