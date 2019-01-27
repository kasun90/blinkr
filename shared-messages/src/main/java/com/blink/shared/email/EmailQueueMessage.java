package com.blink.shared.email;

import java.util.Map;
import com.blink.utilities.BlinkJSON;

public class EmailQueueMessage {
	private EmailType type;
	private String subject;
	private String recipient;
	private Map<String, String> data;

	public EmailQueueMessage() {}

	public EmailQueueMessage(EmailType type, String subject, String recipient, Map<String, String> data) {
		this.type = type;
		this.subject = subject;
		this.recipient = recipient;
		this.data = data;
	}

	public EmailType getType() {
		return type;
	}

	public EmailQueueMessage setType(EmailType type) {
		this.type = type;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public EmailQueueMessage setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getRecipient() {
		return recipient;
	}

	public EmailQueueMessage setRecipient(String recipient) {
		this.recipient = recipient;
		return this;
	}

	public Map<String, String> getData() {
		return data;
	}

	public EmailQueueMessage setData(Map<String, String> data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}