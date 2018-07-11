package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class LoginMessage {
	private String username;
	private String password;

	public LoginMessage(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public LoginMessage setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LoginMessage setPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}