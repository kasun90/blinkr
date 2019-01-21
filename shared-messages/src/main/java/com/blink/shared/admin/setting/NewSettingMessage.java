package com.blink.shared.admin.setting;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class NewSettingMessage extends WebRequestMessage {
	private String key;
	private String value;

	public NewSettingMessage() {}

	public NewSettingMessage(String requestID, String key, String value) {
		super(requestID);
		this.key = key;
		this.value = value;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public NewSettingMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
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