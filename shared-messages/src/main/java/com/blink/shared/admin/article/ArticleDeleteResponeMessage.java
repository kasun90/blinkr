package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleDeleteResponeMessage {
	private String key;
	private boolean success;

	public ArticleDeleteResponeMessage() {}

	public ArticleDeleteResponeMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public ArticleDeleteResponeMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ArticleDeleteResponeMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}