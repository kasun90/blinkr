package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class ChangeNameMessage {
	private String newName;

	public ChangeNameMessage() {}

	public ChangeNameMessage(String newName) {
		this.newName = newName;
	}

	public String getNewName() {
		return newName;
	}

	public ChangeNameMessage setNewName(String newName) {
		this.newName = newName;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}