package com.felix.chord;

import java.util.ArrayList;
import java.util.List;

import com.felix.util.Identify;

public abstract class EndPoint{
	protected final NodeImpl node;

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
	
	protected abstract NodeReference getReference(NodeInfo info);

	
	public void addPrecessor(NodeInfo precessor) {
		node.addPrecessor(getReference(precessor));
	}

	public void addSuccessor(NodeInfo successor) {
		node.addSuccessor(getReference(successor));
	}

	public List<NodeInfo> getRouteTable() {
		List<Node> list=node.getRouteTable();
		List<NodeInfo> re=new ArrayList<NodeInfo>(list.size());
		for(Node node:list){
			re.add(node.getNodeInfo());
		}
		return re;
	}

	public void join(NodeInfo info) {
		node.join(getReference(info));
	}
}
