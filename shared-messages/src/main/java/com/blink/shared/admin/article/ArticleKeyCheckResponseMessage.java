package com.blink.shared.admin.article;

import com.blink.utilities.BlinkJSON;

public class ArticleKeyCheckResponseMessage {
	private boolean available;

	public ArticleKeyCheckResponseMessage() {}

	public ArticleKeyCheckResponseMessage(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public ArticleKeyCheckResponseMessage setAvailable(boolean available) {
		this.available = available;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}