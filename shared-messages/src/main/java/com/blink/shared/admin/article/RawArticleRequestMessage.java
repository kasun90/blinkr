package com.blink.shared.admin.article;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class RawArticleRequestMessage extends WebRequestMessage {
	private String key;

	public RawArticleRequestMessage() {}

	public RawArticleRequestMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public RawArticleRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public RawArticleRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}