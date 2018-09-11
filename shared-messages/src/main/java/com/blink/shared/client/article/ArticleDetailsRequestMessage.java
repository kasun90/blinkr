package com.blink.shared.client.article;

import com.blink.utilities.BlinkJSON;

public class ArticleDetailsRequestMessage {
	private String key;

	public ArticleDetailsRequestMessage() {}

	public ArticleDetailsRequestMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleDetailsRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}