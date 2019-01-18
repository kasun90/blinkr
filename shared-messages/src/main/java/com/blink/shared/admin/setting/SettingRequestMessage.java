package com.blink.shared.admin.setting;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class SettingRequestMessage extends WebRequestMessage {

	public SettingRequestMessage(String requestID) {
		super(requestID);
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public SettingRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}