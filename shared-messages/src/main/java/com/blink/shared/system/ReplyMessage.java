package com.blink.shared.system;

import com.blink.utilities.BlinkJSON;

public class ReplyMessage {
	private String requestID;
	private Object data;

	public ReplyMessage() {}

	public ReplyMessage(String requestID, Object data) {
		this.requestID = requestID;
		this.data = data;
	}

	public String getRequestID() {
		return requestID;
	}

	public ReplyMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ReplyMessage setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}