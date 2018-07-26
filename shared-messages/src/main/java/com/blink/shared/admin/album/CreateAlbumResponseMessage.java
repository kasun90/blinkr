package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class CreateAlbumResponseMessage {
	private String key;
	private boolean success;
	private String description;

	public CreateAlbumResponseMessage(String key, boolean success, String description) {
		this.key = key;
		this.success = success;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public CreateAlbumResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public CreateAlbumResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CreateAlbumResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}