package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleActivateMessage {
	private String key;

	public ArticleActivateMessage() {}

	public ArticleActivateMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public ArticleActivateMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}