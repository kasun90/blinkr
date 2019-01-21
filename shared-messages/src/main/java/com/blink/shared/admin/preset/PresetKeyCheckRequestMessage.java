package com.blink.shared.admin.preset;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class PresetKeyCheckRequestMessage extends WebRequestMessage {
	private String key;

	public PresetKeyCheckRequestMessage() {}

	public PresetKeyCheckRequestMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public PresetKeyCheckRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public PresetKeyCheckRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}