package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class CreatePresetRequestMessage {
	private String title;
	private String key;
	private String description;

	public CreatePresetRequestMessage() {}

	public CreatePresetRequestMessage(String title, String key, String description) {
		this.title = title;
		this.key = key;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public CreatePresetRequestMessage setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getKey() {
		return key;
	}

	public CreatePresetRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CreatePresetRequestMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}