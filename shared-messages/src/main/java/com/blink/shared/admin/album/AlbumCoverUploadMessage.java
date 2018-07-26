package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumCoverUploadMessage {
	private String key;
	private String fileContent;

	public AlbumCoverUploadMessage(String key, String fileContent) {
		this.key = key;
		this.fileContent = fileContent;
	}

	public String getKey() {
		return key;
	}

	public AlbumCoverUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getFileContent() {
		return fileContent;
	}

	public AlbumCoverUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}