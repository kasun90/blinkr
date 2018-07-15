package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class AdminRequestMessage {
	private String requestID;
	private Object enclosedMessage;

	public AdminRequestMessage(String requestID, Object enclosedMessage) {
		this.requestID = requestID;
		this.enclosedMessage = enclosedMessage;
	}

	public String getRequestID() {
		return requestID;
	}

	public AdminRequestMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	public Object getEnclosedMessage() {
		return enclosedMessage;
	}

	public AdminRequestMessage setEnclosedMessage(Object enclosedMessage) {
		this.enclosedMessage = enclosedMessage;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}