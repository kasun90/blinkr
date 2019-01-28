package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class Subscription extends Entity {
	private String firstName;
	private String lastName;
	private String email;

	public Subscription() {}

	public Subscription(String key, long timestamp, String firstName, String lastName, String email) {
		super(key, timestamp);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getKey() {
		return super.getKey();
	}

	public Subscription setKey(String key) {
		super.setKey(key);
		return this;
	}

	public long getTimestamp() {
		return super.getTimestamp();
	}

	public Subscription setTimestamp(long timestamp) {
		super.setTimestamp(timestamp);
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Subscription setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Subscription setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Subscription setEmail(String email) {
		this.email = email;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}