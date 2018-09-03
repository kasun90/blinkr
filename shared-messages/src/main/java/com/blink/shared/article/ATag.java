package com.blink.shared.article;

import java.util.Map;
import com.blink.utilities.BlinkJSON;

public class ATag {
	private ATagType type;
	private Map<String, Object> data;

	public ATag() {}

	public ATag(ATagType type, Map<String, Object> data) {
		this.type = type;
		this.data = data;
	}

	public ATagType getType() {
		return type;
	}

	public ATag setType(ATagType type) {
		this.type = type;
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public ATag setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}