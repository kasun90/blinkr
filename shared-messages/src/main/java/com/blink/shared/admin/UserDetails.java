package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class UserDetails {
	private String username;
	private String name;
	private UserType type;
	private String password;
	private String email;

	public UserDetails(String username, String name, UserType type, String password, String email) {
		this.username = username;
		this.name = name;
		this.type = type;
		this.password = password;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public UserDetails setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getName() {
		return name;
	}

	public UserDetails setName(String name) {
		this.name = name;
		return this;
	}

	public UserType getType() {
		return type;
	}

	public UserDetails setType(UserType type) {
		this.type = type;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserDetails setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserDetails setEmail(String email) {
		this.email = email;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}