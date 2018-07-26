package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class CreateAlbumRequestMessage {
	private String title;
	private String key;
	private String description;

	public CreateAlbumRequestMessage(String title, String key, String description) {
		this.title = title;
		this.key = key;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public CreateAlbumRequestMessage setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getKey() {
		return key;
	}

	public CreateAlbumRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CreateAlbumRequestMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}