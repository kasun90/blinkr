package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;
import com.blink.shared.common.File;

public class FileUploadResponseMessage {
	private boolean success;
	private File file;

	public FileUploadResponseMessage() {}

	public FileUploadResponseMessage(boolean success, File file) {
		this.success = success;
		this.file = file;
	}

	public boolean isSuccess() {
		return success;
	}

	public FileUploadResponseMessage setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public File getFile() {
		return file;
	}

	public FileUploadResponseMessage setFile(File file) {
		this.file = file;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}