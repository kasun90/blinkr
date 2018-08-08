package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class Entity {
	private String key;
	private long timestamp;

	public Entity() {}

	public Entity(String key, long timestamp) {
		this.key = key;
		this.timestamp = timestamp;
	}

	public String getKey() {
		return key;
	}

	public Entity setKey(String key) {
		this.key = key;
		return this;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Entity setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}