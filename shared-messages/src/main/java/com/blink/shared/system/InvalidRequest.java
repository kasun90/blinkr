package com.blink.shared.system;

import com.blink.utilities.BlinkJSON;

public class InvalidRequest {
	private String reason;

	public InvalidRequest() {}

	public InvalidRequest(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public InvalidRequest setReason(String reason) {
		this.reason = reason;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}