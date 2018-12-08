package com.blink.shared.client;

import com.blink.utilities.BlinkJSON;

public class ClientRequestMessage {
	private String requestID;
	private String remoteAddress;
	private Object enclosedMessage;

	public ClientRequestMessage() {}

	public ClientRequestMessage(String requestID, String remoteAddress, Object enclosedMessage) {
		this.requestID = requestID;
		this.remoteAddress = remoteAddress;
		this.enclosedMessage = enclosedMessage;
	}

	public String getRequestID() {
		return requestID;
	}

	public ClientRequestMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public ClientRequestMessage setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
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