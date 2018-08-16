package com.blink.shared.admin.preset;

import com.blink.utilities.BlinkJSON;

public class PresetBeforePhotoUploadResponseMessage {
	private String key;
	private boolean success;

	public PresetBeforePhotoUploadResponseMessage() {}

	public PresetBeforePhotoUploadResponseMessage(String key, boolean success) {
		this.key = key;
		this.success = success;
	}

	public String getKey() {
		return key;
	}

	public PresetBeforePhotoUploadResponseMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public PresetBeforePhotoUploadResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}