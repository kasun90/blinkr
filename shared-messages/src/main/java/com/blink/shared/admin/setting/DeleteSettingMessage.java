package com.blink.shared.admin.setting;

import com.blink.utilities.BlinkJSON;

public class DeleteSettingMessage {
	private String key;

	public DeleteSettingMessage() {}

	public DeleteSettingMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public DeleteSettingMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}