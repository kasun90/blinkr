package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class ChangePasswordResponseMessage {
	private boolean success;
	private String description;

	public ChangePasswordResponseMessage() {}

	public ChangePasswordResponseMessage(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public ChangePasswordResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ChangePasswordResponseMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}