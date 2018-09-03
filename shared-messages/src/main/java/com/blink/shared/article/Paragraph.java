package com.blink.shared.article;

import java.util.List;
import com.blink.utilities.BlinkJSON;

public class Paragraph extends ATag {
	private List<ATag> children;

	public Paragraph() {}

	public Paragraph(ATagType type, List<ATag> children) {
		super(type);
		this.children = children;
	}

	public ATagType getType() {
		return super.getType();
	}

	public Paragraph setType(ATagType type) {
		super.setType(type);
		return this;
	}

	public List<ATag> getChildren() {
		return children;
	}

	public Paragraph setChildren(List<ATag> children) {
		this.children = children;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}