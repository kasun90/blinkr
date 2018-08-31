package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class ChangePasswordMessage {
	private String oldPassword;
	private String newPassword;

	public ChangePasswordMessage() {}

	public ChangePasswordMessage(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public ChangePasswordMessage setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
		return this;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public ChangePasswordMessage setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}