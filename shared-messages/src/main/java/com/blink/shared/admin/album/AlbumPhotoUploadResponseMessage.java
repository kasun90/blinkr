package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumPhotoUploadResponseMessage {
	private String key;

	public AlbumPhotoUploadResponseMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public AlbumPhotoUploadResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}