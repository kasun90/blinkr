package com.blink.shared.email;

import java.util.Map;
import com.blink.utilities.BlinkJSON;

public class BulkEmailQueueMessage {
	private EmailType type;
	private String subject;
	private Map<String, String> data;

	public BulkEmailQueueMessage() {}

	public BulkEmailQueueMessage(EmailType type, String subject, Map<String, String> data) {
		this.type = type;
		this.subject = subject;
		this.data = data;
	}

	public EmailType getType() {
		return type;
	}

	public BulkEmailQueueMessage setType(EmailType type) {
		this.type = type;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public BulkEmailQueueMessage setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public Map<String, String> getData() {
		return data;
	}

	public BulkEmailQueueMessage setData(Map<String, String> data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}