package com.blink.shared.admin.article;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class UpdateArticleRequestMessage extends WebRequestMessage {
	private String key;
	private String content;

	public UpdateArticleRequestMessage() {}

	public UpdateArticleRequestMessage(String requestID, String key, String content) {
		super(requestID);
		this.key = key;
		this.content = content;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public UpdateArticleRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public UpdateArticleRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getContent() {
		return content;
	}

	public UpdateArticleRequestMessage setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}