package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class AdminRequestMessage {
	private String requestID;
	private String targetUser;
	private String sessionID;
	private Object enclosedMessage;

	public AdminRequestMessage(String requestID, String targetUser, String sessionID, Object enclosedMessage) {
		this.requestID = requestID;
		this.targetUser = targetUser;
		this.sessionID = sessionID;
		this.enclosedMessage = enclosedMessage;
	}

	public String getRequestID() {
		return requestID;
	}

	public AdminRequestMessage setRequestID(String requestID) {
		this.requestID = requestID;
		return this;
	}

	public String getTargetUser() {
		return targetUser;
	}

	public AdminRequestMessage setTargetUser(String targetUser) {
		this.targetUser = targetUser;
		return this;
	}

	public String getSessionID() {
		return sessionID;
	}

	public AdminRequestMessage setSessionID(String sessionID) {
		this.sessionID = sessionID;
		return this;
	}

	public Object getEnclosedMessage() {
		return enclosedMessage;
	}

	public AdminRequestMessage setEnclosedMessage(Object enclosedMessage) {
		this.enclosedMessage = enclosedMessage;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}