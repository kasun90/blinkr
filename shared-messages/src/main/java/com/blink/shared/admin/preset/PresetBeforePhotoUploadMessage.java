package com.blink.shared.admin.preset;

import com.blink.shared.admin.FileUploadMessage;
import com.blink.utilities.BlinkJSON;

public class PresetBeforePhotoUploadMessage extends FileUploadMessage {
	private String key;

	public PresetBeforePhotoUploadMessage() {}

	public PresetBeforePhotoUploadMessage(String fileContent, String key) {
		super(fileContent);
		this.key = key;
	}

	public String getFileContent() {
		return super.getFileContent();
	}

	public PresetBeforePhotoUploadMessage setFileContent(String fileContent) {
		super.setFileContent(fileContent);
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