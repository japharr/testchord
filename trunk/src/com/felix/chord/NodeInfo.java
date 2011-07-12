package com.felix.chord;

import com.felix.util.Identify;

public class NodeInfo {

	private final String name;
	private final Identify id;

	public NodeInfo(String name, Identify id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Identify getId() {
		return id;
	}

}
