package com.blink.shared.admin.setting;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class DeleteSettingMessage extends WebRequestMessage {
	private String key;

	public DeleteSettingMessage() {}

	public DeleteSettingMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public DeleteSettingMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
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