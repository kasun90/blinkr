package com.blink.shared.article;

import com.blink.utilities.BlinkJSON;

public class Image extends ATag {
	private String source;
	private String caption;

	public Image() {}

	public Image(ATagType type, String source, String caption) {
		super(type);
		this.source = source;
		this.caption = caption;
	}

	public ATagType getType() {
		return super.getType();
	}

	public Image setType(ATagType type) {
		super.setType(type);
		return this;
	}

	public String getSource() {
		return source;
	}

	public Image setSource(String source) {
		this.source = source;
		return this;
	}

	public String getCaption() {
		return caption;
	}

	public Image setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}