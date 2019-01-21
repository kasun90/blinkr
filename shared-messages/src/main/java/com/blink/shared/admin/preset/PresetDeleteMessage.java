package com.blink.shared.admin.preset;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class PresetDeleteMessage extends WebRequestMessage {
	private String key;

	public PresetDeleteMessage() {}

	public PresetDeleteMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public PresetDeleteMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public PresetDeleteMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}