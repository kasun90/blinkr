package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumKeyCheckRequestMessage {
	private String key;

	public AlbumKeyCheckRequestMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public AlbumKeyCheckRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}