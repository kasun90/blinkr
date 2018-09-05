package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class UpdateArticleResponseMessage {
	private boolean success;
	private String description;

	public UpdateArticleResponseMessage() {}

	public UpdateArticleResponseMessage(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public UpdateArticleResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public UpdateArticleResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}