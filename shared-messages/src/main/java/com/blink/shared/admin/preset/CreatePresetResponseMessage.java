package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class CreatePresetResponseMessage {
	private String key;
	private boolean success;
	private String description;

	public CreatePresetResponseMessage() {}

	public CreatePresetResponseMessage(String key, boolean success, String description) {
		this.key = key;
		this.success = success;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public CreatePresetResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public CreatePresetResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CreatePresetResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}