package com.blink.shared.system;

import com.blink.utilities.BlinkJSON;

public class WebRequestMessage {
	private String requestID;

	public WebRequestMessage() {}

	public WebRequestMessage(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestID() {
		return requestID;
	}

	public WebRequestMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}