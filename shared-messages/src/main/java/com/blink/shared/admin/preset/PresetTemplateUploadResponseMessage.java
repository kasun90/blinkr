package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetTemplateUploadResponseMessage {
	private String key;
	private boolean success;

	public PresetTemplateUploadResponseMessage() {}

	public PresetTemplateUploadResponseMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public PresetTemplateUploadResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public PresetTemplateUploadResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}