package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class RawArticleRequestMessage {
	private String key;

	public RawArticleRequestMessage() {}

	public RawArticleRequestMessage(String key) {
		this.key = key;
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