package com.blink.shared.admin.preset;

import com.blink.shared.admin.FileUploadMessage;
import com.blink.utilities.BlinkJSON;

public class PresetTemplateUploadMessage extends FileUploadMessage {
	private String key;

	public PresetTemplateUploadMessage() {}

	public PresetTemplateUploadMessage(String fileName, String content, String key) {
		super(fileName, content);
		this.key = key;
	}

	public String getFileName() {
		return super.getFileName();
	}

	public PresetTemplateUploadMessage setFileName(String fileName) {
		super.setFileName(fileName);
		return this;
	}

	public String getContent() {
		return super.getContent();
	}

	public PresetTemplateUploadMessage setContent(String content) {
		super.setContent(content);
		return this;
	}

	public String getKey() {
		return key;
	}

	public PresetTemplateUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}