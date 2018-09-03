package com.blink.shared.article;

import com.blink.utilities.BlinkJSON;

public class Header extends ATag {
	private String value;
	private int size;

	public Header() {}

	public Header(ATagType type, String value, int size) {
		super(type);
		this.value = value;
		this.size = size;
	}

	public ATagType getType() {
		return super.getType();
	}

	public Header setType(ATagType type) {
		super.setType(type);
		return this;
	}

	public String getValue() {
		return value;
	}

	public Header setValue(String value) {
		this.value = value;
		return this;
	}

	public int getSize() {
		return size;
	}

	public Header setSize(int size) {
		this.size = size;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}