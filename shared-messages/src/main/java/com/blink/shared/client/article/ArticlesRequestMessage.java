package com.blink.shared.client.article;

import com.blink.utilities.BlinkJSON;

public class ArticlesRequestMessage {
	private long timestamp;
	private boolean less;
	private int limit;

	public ArticlesRequestMessage() {}

	public ArticlesRequestMessage(long timestamp, boolean less, int limit) {
		this.timestamp = timestamp;
		this.less = less;
		this.limit = limit;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public ArticlesRequestMessage setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public boolean isLess() {
		return less;
	}

	public ArticlesRequestMessage setLess(boolean less) {
		this.less = less;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public ArticlesRequestMessage setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}