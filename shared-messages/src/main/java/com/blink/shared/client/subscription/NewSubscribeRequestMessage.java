package com.blink.shared.client.subscription;

import com.blink.utilities.BlinkJSON;

public class NewSubscribeRequestMessage {
	private String firstName;
	private String lastName;
	private String email;
	private String recaptchaToken;

	public NewSubscribeRequestMessage() {}

	public NewSubscribeRequestMessage(String firstName, String lastName, String email, String recaptchaToken) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.recaptchaToken = recaptchaToken;
	}

	public String getFirstName() {
		return firstName;
	}

	public NewSubscribeRequestMessage setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public NewSubscribeRequestMessage setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public NewSubscribeRequestMessage setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRecaptchaToken() {
		return recaptchaToken;
	}

	public NewSubscribeRequestMessage setRecaptchaToken(String recaptchaToken) {
		this.recaptchaToken = recaptchaToken;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}