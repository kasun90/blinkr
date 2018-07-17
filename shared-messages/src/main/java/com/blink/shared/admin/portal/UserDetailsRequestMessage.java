package com.blink.shared.admin.portal;

import com.blink.utilities.BlinkJSON;

public class UserDetailsRequestMessage {

	public UserDetailsRequestMessage() {
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}