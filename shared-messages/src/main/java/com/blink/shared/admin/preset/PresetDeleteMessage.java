package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetDeleteMessage {
	private String key;

	public PresetDeleteMessage() {}

	public PresetDeleteMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public PresetDeleteMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}