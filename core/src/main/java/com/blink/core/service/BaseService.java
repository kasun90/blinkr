package com.blink.core.service;

public abstract class BaseService {
    public abstract String getServiceName();

    private Context context;

    public BaseService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
