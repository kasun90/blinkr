package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class LoginReplyMessage {
	private String sessionID;
	private int code;
	private String message;

	public LoginReplyMessage(String sessionID, int code, String message) {
		this.sessionID = sessionID;
		this.code = code;
		this.message = message;
	}

	public String getSessionID() {
		return sessionID;
	}

	public LoginReplyMessage setSessionID(String sessionID) {
		this.sessionID = sessionID;
		return this;
	}

	public int getCode() {
		return code;
	}

	public LoginReplyMessage setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public LoginReplyMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}