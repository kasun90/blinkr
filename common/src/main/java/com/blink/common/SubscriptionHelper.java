package com.blink.common;

import com.blink.core.service.Context;
import com.blink.shared.common.Subscription;

public class SubscriptionHelper extends CommonHelper<Subscription> {

    public SubscriptionHelper(Context context) {
        super(context, "subscriptions",
                context.getFileService().newFileURI("subscriptions").build(),
                Subscription.class);
    }

    @Override
    public Subscription fillEntity(Subscription entity) throws Exception {
        return entity;
    }

    @Override
    public void cleanupEntityGarbage(String key) throws Exception {
        logger.info("No garbage");
    }
}
