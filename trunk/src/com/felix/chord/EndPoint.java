package com.felix.chord;

import com.felix.util.Identify;

public abstract class EndPoint {
	private final NodeImpl node;

	public EndPoint(NodeImpl node) {
		this.node = node;
	}
	
	
	public NodeInfo findSuccessor(Identify key){
		Node suc=node.findSuccessor(key);
		return new NodeInfo(suc.getName(), suc.getIdentify());
	}

	public NodeInfo getSuccessor(){
		Node suc=node.getSuccessor();
		return new NodeInfo(suc.getName(), suc.getIdentify());
	}

	public NodeInfo getPrecessor(){
		Node suc=node.getPrecessor();
		return new NodeInfo(suc.getName(), suc.getIdentify());
	}
}
