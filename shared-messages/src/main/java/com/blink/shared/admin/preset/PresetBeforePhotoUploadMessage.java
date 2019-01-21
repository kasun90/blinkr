package com.blink.shared.admin.preset;

import com.blink.shared.admin.FileUploadMessage;
import com.blink.utilities.BlinkJSON;

public class PresetBeforePhotoUploadMessage extends FileUploadMessage {
	private String key;

	public PresetBeforePhotoUploadMessage() {}

	public PresetBeforePhotoUploadMessage(String requestID, String fileName, String content, String key) {
		super(requestID, fileName, content);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public PresetBeforePhotoUploadMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getFileName() {
		return super.getFileName();
	}

	public PresetBeforePhotoUploadMessage setFileName(String fileName) {
		super.setFileName(fileName);
		return this;
	}

	public String getContent() {
		return super.getContent();
	}

	public PresetBeforePhotoUploadMessage setContent(String content) {
		super.setContent(content);
		return this;
	}

	public String getKey() {
		return key;
	}

	public PresetBeforePhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}