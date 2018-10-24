package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleDeactivateMessage {
	private String key;

	public ArticleDeactivateMessage() {}

	public ArticleDeactivateMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleDeactivateMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}