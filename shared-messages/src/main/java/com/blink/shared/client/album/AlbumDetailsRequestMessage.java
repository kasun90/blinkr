package com.blink.shared.client.album;

import com.blink.utilities.BlinkJSON;

public class AlbumDetailsRequestMessage {
	private String key;

	public AlbumDetailsRequestMessage(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public AlbumDetailsRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}