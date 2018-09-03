package com.blink.shared.article;

import com.blink.utilities.BlinkJSON;

public class ATag {
	private ATagType type;

	public ATag() {}

	public ATag(ATagType type) {
		this.type = type;
	}

	public ATagType getType() {
		return type;
	}

	public ATag setType(ATagType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}