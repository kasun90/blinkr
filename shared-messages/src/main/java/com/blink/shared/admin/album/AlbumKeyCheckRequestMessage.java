package com.blink.shared.admin.album;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class AlbumKeyCheckRequestMessage extends WebRequestMessage {
	private String key;

	public AlbumKeyCheckRequestMessage() {}

	public AlbumKeyCheckRequestMessage(String requestID, String key) {
		super(requestID);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public AlbumKeyCheckRequestMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public AlbumKeyCheckRequestMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}