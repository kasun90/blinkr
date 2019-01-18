package com.blink.shared.admin.portal;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class ChangeNameMessage extends WebRequestMessage {
	private String newName;

	public ChangeNameMessage() {}

	public ChangeNameMessage(String requestID, String newName) {
		super(requestID);
		this.newName = newName;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public ChangeNameMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
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