package com.blink.shared.admin.preset;

import com.blink.shared.admin.FileUploadMessage;
import com.blink.utilities.BlinkJSON;

public class PresetAfterPhotoUploadMessage extends FileUploadMessage {
	private String key;

	public PresetAfterPhotoUploadMessage() {}

	public PresetAfterPhotoUploadMessage(String requestID, String fileName, String content, String key) {
		super(requestID, fileName, content);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public PresetAfterPhotoUploadMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getFileName() {
		return super.getFileName();
	}

	public PresetAfterPhotoUploadMessage setFileName(String fileName) {
		super.setFileName(fileName);
		return this;
	}

	public String getContent() {
		return super.getContent();
	}

	public PresetAfterPhotoUploadMessage setContent(String content) {
		super.setContent(content);
		return this;
	}

	public String getKey() {
		return key;
	}

	public PresetAfterPhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}