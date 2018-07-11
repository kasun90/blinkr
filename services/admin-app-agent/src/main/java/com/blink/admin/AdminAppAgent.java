package com.blink.admin;

import com.blink.core.service.BaseService;
import com.blink.core.service.Context;

public class AdminAppAgent extends BaseService {

    public AdminAppAgent(Context context) {
        super(context);
    }

    @Override
    public String getServiceName() {
        return "ADMIN";
    }
}
