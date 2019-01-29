package com.blink.shared.admin.subcription;

import java.util.List;
import com.blink.shared.common.Subscription;
import com.blink.utilities.BlinkJSON;

public class SubscriptionsResponseMessage {
	private List<Subscription> subscriptions;
	private int total;

	public SubscriptionsResponseMessage() {}

	public SubscriptionsResponseMessage(List<Subscription> subscriptions, int total) {
		this.subscriptions = subscriptions;
		this.total = total;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public SubscriptionsResponseMessage setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public SubscriptionsResponseMessage setTotal(int total) {
		this.total = total;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}