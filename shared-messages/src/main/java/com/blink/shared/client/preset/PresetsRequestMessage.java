package com.blink.shared.client.preset;

import com.blink.utilities.BlinkJSON;

public class PresetsRequestMessage {
	private long timestamp;
	private boolean less;
	private int limit;

	public PresetsRequestMessage() {}

	public PresetsRequestMessage(long timestamp, boolean less, int limit) {
		this.timestamp = timestamp;
		this.less = less;
		this.limit = limit;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public PresetsRequestMessage setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public boolean isLess() {
		return less;
	}

	public PresetsRequestMessage setLess(boolean less) {
		this.less = less;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public PresetsRequestMessage setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}