package com.blink.shared.admin.setting;

import com.blink.utilities.BlinkJSON;

public class SettingRequestMessage {

	public SettingRequestMessage() {
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}