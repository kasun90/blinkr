package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetKeyCheckRequestMessage {
	private String key;

	public PresetKeyCheckRequestMessage() {}

	public PresetKeyCheckRequestMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public PresetKeyCheckRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}