package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumKeyCheckResponseMessage {
	private boolean available;

	public AlbumKeyCheckResponseMessage(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public AlbumKeyCheckResponseMessage setAvailable(boolean available) {
		this.available = available;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}