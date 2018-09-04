package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class CreateArticleResponseMessage {
	private String key;
	private boolean success;
	private String description;

	public CreateArticleResponseMessage() {}

	public CreateArticleResponseMessage(String key, boolean success, String description) {
		this.key = key;
		this.success = success;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public CreateArticleResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public CreateArticleResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CreateArticleResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}