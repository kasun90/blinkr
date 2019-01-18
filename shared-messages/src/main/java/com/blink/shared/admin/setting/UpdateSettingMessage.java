package com.blink.shared.admin.setting;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class UpdateSettingMessage extends WebRequestMessage {
	private String key;
	private String value;

	public UpdateSettingMessage() {}

	public UpdateSettingMessage(String requestID, String key, String value) {
		super(requestID);
		this.key = key;
		this.value = value;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public UpdateSettingMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public UpdateSettingMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public UpdateSettingMessage setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}