package com.blink.shared.admin.portal;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class ChangePasswordMessage extends WebRequestMessage {
	private String oldPassword;
	private String newPassword;

	public ChangePasswordMessage() {}

	public ChangePasswordMessage(String requestID, String oldPassword, String newPassword) {
		super(requestID);
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public ChangePasswordMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
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