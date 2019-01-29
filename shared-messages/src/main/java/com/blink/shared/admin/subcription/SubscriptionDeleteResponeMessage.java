package com.blink.shared.admin.subcription;

import com.blink.utilities.BlinkJSON;

public class SubscriptionDeleteResponeMessage {
	private String key;
	private boolean success;

	public SubscriptionDeleteResponeMessage() {}

	public SubscriptionDeleteResponeMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public SubscriptionDeleteResponeMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public SubscriptionDeleteResponeMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}