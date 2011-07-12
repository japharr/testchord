package com.felix.chord.local;

import java.util.List;

import com.felix.chord.AbstractNode;
import com.felix.chord.Node;
import com.felix.chord.NodeImpl;
import com.felix.chord.NodeInfo;
import com.felix.util.Identify;

public class LocalNodeReference extends AbstractNode implements Node{
	

	private final NodeImpl node;
	

	public LocalNodeReference(String name, Identify id) {
		super(name, id);
		this.node=LocalNodeFactory.getNode(id);
	}
	
	public LocalNodeReference(NodeInfo info){
		this(info.getName(), info.getId());
	}
	
	public LocalNodeReference(Node node){
		this(node.getNodeInfo());
	}
	
	public LocalNodeReference(String name, Identify id,NodeImpl impl) {
		super(name, id);
		this.node=impl;
	}

	@Override
	public void addPrecessor(Node precessor) {
		node.addPrecessor(precessor);
	}

	@Override
	public void addSuccessor(Node successor) {
		node.addSuccessor(successor);
	}

	@Override
	public Node findSuccessor(Identify key) {
		return new LocalNodeReference(node.findSuccessor(key));
	}

	@Override
	public Node getPrecessor() {
		return new LocalNodeReference(node.getPrecessor());
	}

	@Override
	public Node getSuccessor() {
		return new LocalNodeReference(node.getSuccessor());
	}

	@Override
	public void join(Node nodeOnCycle) {
		node.join(nodeOnCycle);
		
	}

	@Override
	public void updateRouteTable(Node node) {
		node.updateRouteTable(node);
		
	}

	@Override
	public List<Node> getRouteTable() {
		return node.getRouteTable();
	}

	@Override
	public void join() {
		node.join();
		
	}


}
