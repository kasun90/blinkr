package com.blink.shared.admin.album;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class AlbumPhotoUploadMessage extends WebRequestMessage {
	private String key;
	private String fileContent;

	public AlbumPhotoUploadMessage() {}

	public AlbumPhotoUploadMessage(String requestID, String key, String fileContent) {
		super(requestID);
		this.key = key;
		this.fileContent = fileContent;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public AlbumPhotoUploadMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getKey() {
		return key;
	}

	public AlbumPhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getFileContent() {
		return fileContent;
	}

	public AlbumPhotoUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}