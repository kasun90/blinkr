package com.blink.shared.admin.subcription;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class SubscriptionsRequestMessage extends WebRequestMessage {
	private long timestamp;
	private boolean less;
	private int limit;

	public SubscriptionsRequestMessage() {}

	public SubscriptionsRequestMessage(String requestID, long timestamp, boolean less, int limit) {
		super(requestID);
		this.timestamp = timestamp;
		this.less = less;
		this.limit = limit;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public SubscriptionsRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public SubscriptionsRequestMessage setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public boolean isLess() {
		return less;
	}

	public SubscriptionsRequestMessage setLess(boolean less) {
		this.less = less;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public SubscriptionsRequestMessage setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}