package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class UserDetailsResponseMessage {
	private String name;
	private String type;
	private String email;

	public UserDetailsResponseMessage(String name, String type, String email) {
		this.name = name;
		this.type = type;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public UserDetailsResponseMessage setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public UserDetailsResponseMessage setType(String type) {
		this.type = type;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserDetailsResponseMessage setEmail(String email) {
		this.email = email;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}