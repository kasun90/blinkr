package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class ChangeNameResponseMessage {
	private boolean success;
	private String description;

	public ChangeNameResponseMessage() {}

	public ChangeNameResponseMessage(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public ChangeNameResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ChangeNameResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}