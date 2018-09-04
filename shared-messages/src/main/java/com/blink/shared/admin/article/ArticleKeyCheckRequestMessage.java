package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleKeyCheckRequestMessage {
	private String key;

	public ArticleKeyCheckRequestMessage() {}

	public ArticleKeyCheckRequestMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleKeyCheckRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}