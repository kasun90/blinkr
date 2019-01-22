package com.blink.email;

import com.blink.common.BlinkService;
import com.blink.core.service.Context;

public class EmailAgent extends BlinkService {

    public EmailAgent(Context context) {
        super(context);
    }

    @Override
    public String getServiceName() {
        return "EMAIL";
    }
}
