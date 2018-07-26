package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumDeleteMessage {
	private String key;

	public AlbumDeleteMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public AlbumDeleteMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}