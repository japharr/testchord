package com.felix.chord;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.felix.util.Identify;

public abstract class NodeReference extends AbstractNode implements Node {

	protected final EndPoint endPoint;
	
	public NodeReference(String name, Identify id,EndPoint node) {
		super(name, id);
		this.endPoint=node;
	}
	
	protected abstract NodeReference getReference(NodeInfo info);
	
	
	@Override
	public void addPrecessor(Node precessor) {
		endPoint.addPrecessor(precessor.getNodeInfo());
	}

	@Override
	public void addSuccessor(Node successor) {
		endPoint.addSuccessor(successor.getNodeInfo());
	}

	@Override
	public Node findSuccessor(Identify key) {
		return getReference(endPoint.findSuccessor(key));
	}

	@Override
	public Node getPrecessor() {
		return getReference(endPoint.getPrecessor());
	}

	@Override
	public Node getSuccessor() {
		return getReference(endPoint.getSuccessor());
	}

	@Override
	public void join(Node nodeOnCycle) {
		endPoint.join(nodeOnCycle.getNodeInfo());

	}

	@Override
	public void updateRouteTable(Node node) {
		node.updateRouteTable(node);

	}

	@Override
	public List<Node> getRouteTable() {
		List<NodeInfo> list=endPoint.getRouteTable();
		List<Node> re=new ArrayList<Node>(list.size());
		for(NodeInfo info:list){
			re.add(getReference(info));
		}
		return re;
	}

	@Override
	public void join() {
		throw new NotImplementedException();
	}

}
