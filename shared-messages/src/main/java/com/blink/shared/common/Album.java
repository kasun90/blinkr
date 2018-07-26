package com.blink.shared.common;

import java.util.List;
import com.blink.utilities.BlinkJSON;

public class Album {
	private String title;
	private String key;
	private String description;
	private int count;
	private List<Photo> photos;
	private Photo cover;
	private long timestamp;

	public Album(String title, String key, String description, int count, List<Photo> photos, Photo cover, long timestamp) {
		this.title = title;
		this.key = key;
		this.description = description;
		this.count = count;
		this.photos = photos;
		this.cover = cover;
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public Album setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getKey() {
		return key;
	}

	public Album setKey(String key) {
		this.key = key;
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

	public long getTimestamp() {
		return timestamp;
	}

	public Album setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}