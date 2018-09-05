package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class RawArticle {
	private String key;
	private String title;
	private String content;

	public RawArticle() {}

	public RawArticle(String key, String title, String content) {
		this.key = key;
		this.title = title;
		this.content = content;
	}

	public String getKey() {
		return key;
	}

	public RawArticle setKey(String key) {
		this.key = key;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public RawArticle setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public RawArticle setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}