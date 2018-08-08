package com.blink.shared.admin.portal;

import java.util.List;
import com.blink.shared.client.messaging.UserMessage;
import com.blink.utilities.BlinkJSON;

public class UserMessagesResponseMessage {
	private List<UserMessage> messages;
	private long total;

	public UserMessagesResponseMessage() {}

	public UserMessagesResponseMessage(List<UserMessage> messages, long total) {
		this.messages = messages;
		this.total = total;
	}

	public List<UserMessage> getMessages() {
		return messages;
	}

	public UserMessagesResponseMessage setMessages(List<UserMessage> messages) {
		this.messages = messages;
		return this;
	}

	public long getTotal() {
		return total;
	}

	public UserMessagesResponseMessage setTotal(long total) {
		this.total = total;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}