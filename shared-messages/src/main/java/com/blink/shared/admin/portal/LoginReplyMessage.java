package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class LoginReplyMessage {
	private String token;
	private int code;
	private String message;

	public LoginReplyMessage(String token, int code, String message) {
		this.token = token;
		this.code = code;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public LoginReplyMessage setToken(String token) {
		this.token = token;
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