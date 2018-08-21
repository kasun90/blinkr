package com.blink.web.meta;

import com.blink.core.service.Context;

public interface TagHandler {
    void handle(PathParams params, Context context);
}
