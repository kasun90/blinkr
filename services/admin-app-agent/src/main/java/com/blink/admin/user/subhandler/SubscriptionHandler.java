package com.blink.admin.user.subhandler;

import com.blink.admin.user.SubHandler;
import com.blink.admin.user.UserHandler;
import com.blink.common.SubscriptionHelper;
import com.blink.shared.admin.subcription.SubscriptionDeleteMessage;
import com.blink.shared.admin.subcription.SubscriptionDeleteResponeMessage;
import com.blink.shared.admin.subcription.SubscriptionsRequestMessage;
import com.blink.shared.admin.subcription.SubscriptionsResponseMessage;
import com.google.common.eventbus.Subscribe;

public class SubscriptionHandler extends SubHandler {

    private SubscriptionHelper helper;

    public SubscriptionHandler(UserHandler userHandler) {
        super(userHandler);
        helper = new SubscriptionHelper(adminService.getContext());
    }

    @Subscribe
    public void handleSubscriptionRequest(SubscriptionsRequestMessage message) throws Exception {
        adminService.sendReply(message.getRequestID(), new SubscriptionsResponseMessage(
                helper.getEntities(message.getTimestamp(), message.isLess(), message.getLimit()),
                helper.getEntityCount()
        ));
    }

    @Subscribe
    public void handleSubscriptionDelete(SubscriptionDeleteMessage message) throws Exception {
        boolean success = helper.deleteEntity(message.getKey());
        adminService.sendReply(message.getRequestID(), new SubscriptionDeleteResponeMessage(message.getKey(),
                success));
        logger.info("Subscription delete status [success={} key={}]", success, message.getKey());
    }

}
