package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class UpdateArticleRequestMessage {
	private String key;
	private String content;

	public UpdateArticleRequestMessage() {}

	public UpdateArticleRequestMessage(String key, String content) {
		this.key = key;
		this.content = content;
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