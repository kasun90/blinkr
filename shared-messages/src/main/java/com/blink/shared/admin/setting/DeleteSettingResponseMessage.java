package com.blink.shared.admin.setting;

import com.blink.utilities.BlinkJSON;

public class DeleteSettingResponseMessage {
	private String key;

	public DeleteSettingResponseMessage() {}

	public DeleteSettingResponseMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public DeleteSettingResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}