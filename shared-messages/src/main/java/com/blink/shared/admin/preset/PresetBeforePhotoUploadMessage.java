package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetBeforePhotoUploadMessage {
	private String key;
	private String fileContent;

	public PresetBeforePhotoUploadMessage() {}

	public PresetBeforePhotoUploadMessage(String key, String fileContent) {
		this.key = key;
		this.fileContent = fileContent;
	}

	public String getKey() {
		return key;
	}

	public PresetBeforePhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getFileContent() {
		return fileContent;
	}

	public PresetBeforePhotoUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}