package com.blink.client;

import com.blink.core.service.BaseService;
import com.blink.core.service.Context;

public class ClientAppAgent extends BaseService {


    public ClientAppAgent(Context context) {
        super(context);
    }



    @Override
    public String getServiceName() {
        return "CLIENT";
    }
}
