package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class Preset extends Entity {
	private String title;
	private String description;
	private Photo beforeImage;
	private Photo afterImage;
	private File templateFile;
	private long timestamp;

	public Preset() {}

	public Preset(String key, String title, String description, Photo beforeImage, Photo afterImage, File templateFile, long timestamp) {
		super(key);
		this.title = title;
		this.description = description;
		this.beforeImage = beforeImage;
		this.afterImage = afterImage;
		this.templateFile = templateFile;
		this.timestamp = timestamp;
	}

	public String getKey() {
		return super.getKey();
	}

	public Preset setKey(String key) {
		super.setKey(key);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Preset setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Preset setDescription(String description) {
		this.description = description;
		return this;
	}

	public Photo getBeforeImage() {
		return beforeImage;
	}

	public Preset setBeforeImage(Photo beforeImage) {
		this.beforeImage = beforeImage;
		return this;
	}

	public Photo getAfterImage() {
		return afterImage;
	}

	public Preset setAfterImage(Photo afterImage) {
		this.afterImage = afterImage;
		return this;
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public Preset setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
		return this;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Preset setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}