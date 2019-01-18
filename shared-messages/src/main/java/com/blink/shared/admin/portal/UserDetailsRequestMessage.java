package com.blink.shared.admin.portal;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class UserDetailsRequestMessage extends WebRequestMessage {

	public UserDetailsRequestMessage(String requestID) {
		super(requestID);
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public UserDetailsRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}