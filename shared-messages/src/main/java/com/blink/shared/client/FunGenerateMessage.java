package com.blink.shared.client;

import java.util.List;
import com.blink.utilities.BlinkJSON;

public class FunGenerateMessage {
	private String hello;
	private int age;

	public FunGenerateMessage(String hello, int age) {
		this.hello = hello;
		this.age = age;
	}

	public String getHello() {
		return hello;
	}

	public FunGenerateMessage setHello(String hello) {
		this.hello = hello;
		return this;
	}

	public int getAge() {
		return age;
	}

	public FunGenerateMessage setAge(int age) {
		this.age = age;
		return this;
	}

	@Override
	public String toString() {
		return BlinkJSON.toPrettyJSON(this);
	}
}