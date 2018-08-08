package com.blink.shared.common;

import java.util.List;
import com.blink.utilities.BlinkJSON;

public class Album extends Entity {
	private String title;
	private String description;
	private int count;
	private List<Photo> photos;
	private Photo cover;

	public Album() {}

	public Album(String key, long timestamp, String title, String description, int count, List<Photo> photos, Photo cover) {
		super(key, timestamp);
		this.title = title;
		this.description = description;
		this.count = count;
		this.photos = photos;
		this.cover = cover;
	}

	public String getKey() {
		return super.getKey();
	}

	public Album setKey(String key) {
		super.setKey(key);
		return this;
	}

	public long getTimestamp() {
		return super.getTimestamp();
	}

	public Album setTimestamp(long timestamp) {
		super.setTimestamp(timestamp);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Album setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Album setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getCount() {
		return count;
	}

	public Album setCount(int count) {
		this.count = count;
		return this;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public Album setPhotos(List<Photo> photos) {
		this.photos = photos;
		return this;
	}

	public Photo getCover() {
		return cover;
	}

	public Album setCover(Photo cover) {
		this.cover = cover;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}