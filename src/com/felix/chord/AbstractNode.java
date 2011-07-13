package com.felix.chord;

import com.felix.util.Identify;

public abstract class AbstractNode implements Node{
	private final NodeInfo info;

	public AbstractNode(String name,Identify id) {
		this.info = new NodeInfo(name, id);
	}

	public NodeInfo getInfo() {
		return info;
	}
	
	@Override
	public Identify getIdentify() {
		return info.getId();
	}
	
	@Override
	public String getName() {
		return info.getName();
	}
	
	@Override
	public NodeInfo getNodeInfo() {
		return info;
	}

	
	
}
