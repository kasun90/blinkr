package com.blink.shared.admin;

import com.blink.shared.system.WebRequestMessage;
import com.blink.utilities.BlinkJSON;

public class FileUploadMessage extends WebRequestMessage {
	private String fileName;
	private String content;

	public FileUploadMessage() {}

	public FileUploadMessage(String requestID, String fileName, String content) {
		super(requestID);
		this.fileName = fileName;
		this.content = content;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public FileUploadMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
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