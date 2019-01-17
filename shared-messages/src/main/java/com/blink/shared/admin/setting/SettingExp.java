package com.blink.shared.admin.setting;

import com.blink.utilities.BlinkJSON;

public class SettingExp {
	private String key;
	private String value;

	public SettingExp() {}

	public SettingExp(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public SettingExp setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public SettingExp setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}