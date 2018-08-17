package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class FileUploadMessage {
	private String fileContent;

	public FileUploadMessage() {}

	public FileUploadMessage(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileContent() {
		return fileContent;
	}

	public FileUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}