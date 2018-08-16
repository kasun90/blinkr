package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetDeleteResponeMessage {
	private String key;
	private boolean success;

	public PresetDeleteResponeMessage() {}

	public PresetDeleteResponeMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public PresetDeleteResponeMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public PresetDeleteResponeMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}