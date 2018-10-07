package com.blink.shared.client.article;

import java.util.List;
import com.blink.utilities.BlinkJSON;
import com.blink.shared.common.Article;

public class ArticleSearchResponseMessage {
	private List<Article> articles;

	public ArticleSearchResponseMessage() {}

	public ArticleSearchResponseMessage(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public ArticleSearchResponseMessage setArticles(List<Article> articles) {
		this.articles = articles;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}