package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class RawArticleResponseMessage {
	private String key;
	private String title;
	private String content;

	public RawArticleResponseMessage() {}

	public RawArticleResponseMessage(String key, String title, String content) {
		this.key = key;
		this.title = title;
		this.content = content;
	}

	public String getKey() {
		return key;
	}

	public RawArticleResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public RawArticleResponseMessage setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public RawArticleResponseMessage setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}