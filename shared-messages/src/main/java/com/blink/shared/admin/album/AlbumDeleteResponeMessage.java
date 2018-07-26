package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumDeleteResponeMessage {
	private String key;
	private boolean success;

	public AlbumDeleteResponeMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public AlbumDeleteResponeMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public AlbumDeleteResponeMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}