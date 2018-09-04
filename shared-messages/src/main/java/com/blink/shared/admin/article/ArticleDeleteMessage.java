package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleDeleteMessage {
	private String key;

	public ArticleDeleteMessage() {}

	public ArticleDeleteMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleDeleteMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}