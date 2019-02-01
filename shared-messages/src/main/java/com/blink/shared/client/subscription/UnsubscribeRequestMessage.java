package com.blink.shared.client.subscription;

import com.blink.utilities.BlinkJSON;

public class UnsubscribeRequestMessage {
	private String email;
	private String recaptchaToken;

	public UnsubscribeRequestMessage() {}

	public UnsubscribeRequestMessage(String email, String recaptchaToken) {
		this.email = email;
		this.recaptchaToken = recaptchaToken;
	}

	public String getEmail() {
		return email;
	}

	public UnsubscribeRequestMessage setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRecaptchaToken() {
		return recaptchaToken;
	}

	public UnsubscribeRequestMessage setRecaptchaToken(String recaptchaToken) {
		this.recaptchaToken = recaptchaToken;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}