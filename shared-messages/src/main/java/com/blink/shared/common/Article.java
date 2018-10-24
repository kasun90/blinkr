package com.blink.shared.common;

import java.util.List;
import com.blink.utilities.BlinkJSON;
import com.blink.shared.article.ATag;

public class Article extends Entity {
	private String title;
	private String description;
	private String author;
	private List<ATag> tags;
	private long updated;
	private long views;
	private boolean active;

	public Article() {}

	public Article(String key, long timestamp, String title, String description, String author, List<ATag> tags, long updated, long views, boolean active) {
		super(key, timestamp);
		this.title = title;
		this.description = description;
		this.author = author;
		this.tags = tags;
		this.updated = updated;
		this.views = views;
		this.active = active;
	}

	public String getKey() {
		return super.getKey();
	}

	public Article setKey(String key) {
		super.setKey(key);
		return this;
	}

	public long getTimestamp() {
		return super.getTimestamp();
	}

	public Article setTimestamp(long timestamp) {
		super.setTimestamp(timestamp);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Article setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Article setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Article setAuthor(String author) {
		this.author = author;
		return this;
	}

	public List<ATag> getTags() {
		return tags;
	}

	public Article setTags(List<ATag> tags) {
		this.tags = tags;
		return this;
	}

	public long getUpdated() {
		return updated;
	}

	public Article setUpdated(long updated) {
		this.updated = updated;
		return this;
	}

	public long getViews() {
		return views;
	}

	public Article setViews(long views) {
		this.views = views;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public Article setActive(boolean active) {
		this.active = active;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}