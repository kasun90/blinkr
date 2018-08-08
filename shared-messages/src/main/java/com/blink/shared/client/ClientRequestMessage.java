package com.blink.shared.client;

import com.blink.utilities.BlinkJSON;

public class ClientRequestMessage {
	private String requestID;
	private Object enclosedMessage;

	public ClientRequestMessage() {}

	public ClientRequestMessage(String requestID, Object enclosedMessage) {
		this.requestID = requestID;
		this.enclosedMessage = enclosedMessage;
	}

	public String getRequestID() {
		return requestID;
	}

	public ClientRequestMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	public Object getEnclosedMessage() {
		return enclosedMessage;
	}

	public ClientRequestMessage setEnclosedMessage(Object enclosedMessage) {
		this.enclosedMessage = enclosedMessage;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}