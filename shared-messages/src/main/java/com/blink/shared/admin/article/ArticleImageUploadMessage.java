package com.blink.shared.admin.article;

import com.blink.shared.admin.FileUploadMessage;
import com.blink.utilities.BlinkJSON;

public class ArticleImageUploadMessage extends FileUploadMessage {
	private String key;

	public ArticleImageUploadMessage() {}

	public ArticleImageUploadMessage(String requestID, String fileName, String content, String key) {
		super(requestID, fileName, content);
		this.key = key;
	}

	public String getRequestID() {
		return super.getRequestID();
	}

	public ArticleImageUploadMessage setRequestID(String requestID) {
		super.setRequestID(requestID);
		return this;
	}

	public String getFileName() {
		return super.getFileName();
	}

	public ArticleImageUploadMessage setFileName(String fileName) {
		super.setFileName(fileName);
		return this;
	}

	public String getContent() {
		return super.getContent();
	}

	public ArticleImageUploadMessage setContent(String content) {
		super.setContent(content);
		return this;
	}

	public String getKey() {
		return key;
	}

	public ArticleImageUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}