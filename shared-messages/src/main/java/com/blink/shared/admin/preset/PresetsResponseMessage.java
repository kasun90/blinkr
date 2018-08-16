package com.blink.shared.admin.preset;

import java.util.List;
import com.blink.shared.common.Preset;
import com.blink.utilities.BlinkJSON;

public class PresetsResponseMessage {
	private List<Preset> presets;
	private int total;

	public PresetsResponseMessage() {}

	public PresetsResponseMessage(List<Preset> presets, int total) {
		this.presets = presets;
		this.total = total;
	}

	public List<Preset> getPresets() {
		return presets;
	}

	public PresetsResponseMessage setPresets(List<Preset> presets) {
		this.presets = presets;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public PresetsResponseMessage setTotal(int total) {
		this.total = total;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}