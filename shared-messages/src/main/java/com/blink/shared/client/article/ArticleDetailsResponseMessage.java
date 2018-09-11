package com.blink.shared.client.article;

import com.blink.utilities.BlinkJSON;
import com.blink.shared.common.Article;

public class ArticleDetailsResponseMessage {
	private Article article;

	public ArticleDetailsResponseMessage() {}

	public ArticleDetailsResponseMessage(Article article) {
		this.article = article;
	}

	public Article getArticle() {
		return article;
	}

	public ArticleDetailsResponseMessage setArticle(Article article) {
		this.article = article;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}