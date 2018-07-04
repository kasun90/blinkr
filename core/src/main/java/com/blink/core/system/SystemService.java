package com.blink.core.system;

import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.system.WebInMessage;
import com.blink.shared.system.WebOutMessage;
import com.google.common.eventbus.Subscribe;

public class SystemService extends BaseService {


    public SystemService(Context context) {
        super(context);
    }

    @Subscribe
    public void onWebIn(WebInMessage message) {
        String tempplayload = null;
        getContext().getBus().post(new WebOutMessage(message.getRequestID(), tempplayload.replace("Kasun", "Frank")));
    }

    @Override
    public String getServiceName() {
        return "SYSTEM";
    }
}
