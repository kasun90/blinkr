package com.blink.shared.admin.album;

import com.blink.utilities.BlinkJSON;

public class AlbumPhotoUploadMessage {
	private String key;
	private String fileContent;

	public AlbumPhotoUploadMessage(String key, String fileContent) {
		this.key = key;
		this.fileContent = fileContent;
	}

	public String getKey() {
		return key;
	}

	public AlbumPhotoUploadMessage setKey(String key) {
		this.key = key;
		return this;
	}

	public String getFileContent() {
		return fileContent;
	}

	public AlbumPhotoUploadMessage setFileContent(String fileContent) {
		this.fileContent = fileContent;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}