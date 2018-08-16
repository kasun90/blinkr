package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetKeyCheckResponseMessage {
	private boolean available;

	public PresetKeyCheckResponseMessage() {}

	public PresetKeyCheckResponseMessage(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public PresetKeyCheckResponseMessage setAvailable(boolean available) {
		this.available = available;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}