package com.blink.shared.client.messaging;

import com.blink.utilities.BlinkJSON;

public class UserMessage {
	private String message;
	private String email;
	private String phone;

	public UserMessage(String message, String email, String phone) {
		this.message = message;
		this.email = email;
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public UserMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserMessage setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public UserMessage setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}