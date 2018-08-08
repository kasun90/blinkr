package com.blink.shared.common;

import com.blink.utilities.BlinkJSON;

public class Photo {
	private String resource;
	private String url;

	public Photo() {}

	public Photo(String resource, String url) {
		this.resource = resource;
		this.url = url;
	}

	public String getResource() {
		return resource;
	}

	public Photo setResource(String resource) {
		this.resource = resource;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Photo setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}