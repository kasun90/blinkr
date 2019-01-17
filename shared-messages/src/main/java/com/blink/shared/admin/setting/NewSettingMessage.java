package com.blink.shared.admin.setting;

import com.blink.utilities.BlinkJSON;

public class NewSettingMessage {
	private String key;
	private String value;

	public NewSettingMessage() {}

	public NewSettingMessage(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public NewSettingMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public NewSettingMessage setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}