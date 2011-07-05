package com.felix.util;

public class DataEntry {
	private final String key;
	private final Object data;

	public String getKey() {
		return key;
	}

	public Object getData() {
		return data;
	}

	public DataEntry(String key, Object data) {
		super();
		this.key = key;
		this.data = data;
	}

}
