package com.blink.shared.client;

import com.blink.utilities.BlinkJSON;

public class GenericStatusReplyMessage {
	private boolean status;
	private String description;

	public GenericStatusReplyMessage() {}

	public GenericStatusReplyMessage(boolean status, String description) {
		this.status = status;
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public GenericStatusReplyMessage setStatus(boolean status) {
		this.status = status;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public GenericStatusReplyMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}