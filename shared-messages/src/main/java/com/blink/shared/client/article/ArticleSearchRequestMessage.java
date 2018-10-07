package com.blink.shared.client.article;

import com.blink.utilities.BlinkJSON;

public class ArticleSearchRequestMessage {
	private String keyPhrase;

	public ArticleSearchRequestMessage() {}

	public ArticleSearchRequestMessage(String keyPhrase) {
		this.keyPhrase = keyPhrase;
	}

	public String getKeyPhrase() {
		return keyPhrase;
	}

	public ArticleSearchRequestMessage setKeyPhrase(String keyPhrase) {
		this.keyPhrase = keyPhrase;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}