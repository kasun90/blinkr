package com.blink.shared.client.article;

import com.blink.utilities.BlinkJSON;

public class ArticleViewAckMessage {
	private String key;

	public ArticleViewAckMessage() {}

	public ArticleViewAckMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleViewAckMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}