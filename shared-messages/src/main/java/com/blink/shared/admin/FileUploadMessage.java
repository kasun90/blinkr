package com.blink.shared.admin;

import com.blink.utilities.BlinkJSON;

public class FileUploadMessage {
	private String fileName;
	private String content;

	public FileUploadMessage() {}

	public FileUploadMessage(String fileName, String content) {
		this.fileName = fileName;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public FileUploadMessage setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getContent() {
		return content;
	}

	public FileUploadMessage setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}