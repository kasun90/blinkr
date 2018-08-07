package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class File {
	private String resource;
	private String url;

	public File(String resource, String url) {
		this.resource = resource;
		this.url = url;
	}

	public String getResource() {
		return resource;
	}

	public File setResource(String resource) {
		this.resource = resource;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public File setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}