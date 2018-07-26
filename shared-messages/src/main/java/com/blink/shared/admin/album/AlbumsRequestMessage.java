package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumsRequestMessage {
	private long timestamp;
	private boolean less;
	private int limit;

	public AlbumsRequestMessage(long timestamp, boolean less, int limit) {
		this.timestamp = timestamp;
		this.less = less;
		this.limit = limit;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public AlbumsRequestMessage setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public boolean isLess() {
		return less;
	}

	public AlbumsRequestMessage setLess(boolean less) {
		this.less = less;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public AlbumsRequestMessage setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}