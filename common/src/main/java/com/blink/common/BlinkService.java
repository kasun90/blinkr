package com.blink.common;

import com.blink.core.exception.BlinkRuntimeException;
import com.blink.core.service.BaseService;
import com.blink.core.service.Context;
import com.blink.shared.system.ReplyMessage;

public abstract class BlinkService extends BaseService {
    public BlinkService(Context context) throws Exception {
        super(context);
    }

    @Override
    public void sendReply(String requestID, Object message) {
        if (requestID == null)
            throw new BlinkRuntimeException("Request ID cannot be null. Please set the request ID first");
        getContext().getBus().post(new ReplyMessage(requestID, message));
    }
}
