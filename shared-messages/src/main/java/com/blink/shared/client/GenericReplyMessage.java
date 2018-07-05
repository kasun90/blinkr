package com.blink.shared.client;

import com.blink.utilities.BlinkJSON;

public class GenericReplyMessage {
	private String description;

	public GenericReplyMessage(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public GenericReplyMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}