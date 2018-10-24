package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class ActionResponseMessage {
	private boolean success;
	private String description;

	public ActionResponseMessage() {}

	public ActionResponseMessage(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public ActionResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ActionResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}