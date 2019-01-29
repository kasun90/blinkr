package com.blink.shared.admin.subcription;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class SubscriptionDeleteMessage extends WebRequestMessage {
	private String key;

	public SubscriptionDeleteMessage() {}

	public SubscriptionDeleteMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public SubscriptionDeleteMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public SubscriptionDeleteMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}