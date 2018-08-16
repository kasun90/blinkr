package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetAfterPhotoUploadMessage {
	private String key;
	private String fileContent;

	public PresetAfterPhotoUploadMessage() {}

	public PresetAfterPhotoUploadMessage(String key, String fileContent) {
		this.key = key;
		this.fileContent = fileContent;
	}

	public String getKey() {
		return key;
	}

	public PresetAfterPhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getFileContent() {
		return fileContent;
	}

	public PresetAfterPhotoUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}