package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class Entity {
	private String key;

	public Entity() {}

	public Entity(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public Entity setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}