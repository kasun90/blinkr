package com.blink.shared.admin.article;

import java.util.List;
import com.blink.utilities.BlinkJSON;
import com.blink.shared.common.Article;

public class ArticlesResponseMessage {
	private List<Article> articles;
	private int total;

	public ArticlesResponseMessage() {}

	public ArticlesResponseMessage(List<Article> articles, int total) {
		this.articles = articles;
		this.total = total;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public ArticlesResponseMessage setArticles(List<Article> articles) {
		this.articles = articles;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public ArticlesResponseMessage setTotal(int total) {
		this.total = total;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}