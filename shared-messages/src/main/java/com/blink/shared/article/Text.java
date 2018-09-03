package com.blink.shared.article;

import com.blink.utilities.BlinkJSON;

public class Text extends ATag {
	private String value;

	public Text() {}

	public Text(ATagType type, String value) {
		super(type);
		this.value = value;
	}

	public ATagType getType() {
		return super.getType();
	}

	public Text setType(ATagType type) {
		super.setType(type);
		return this;
	}

	public String getValue() {
		return value;
	}

	public Text setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}