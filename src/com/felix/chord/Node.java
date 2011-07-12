package com.felix.chord;

import java.util.List;

import com.felix.util.Identify;


public interface Node {

	public abstract void join();
	
	public abstract List<Node> getRouteTable();
	
	public abstract Node findSuccessor(Identify key);

	public abstract Node getSuccessor();

	public abstract Node getPrecessor();

	public abstract void addSuccessor(Node successor);

	public abstract void addPrecessor(Node precessor);
	
	public abstract NodeInfo getNodeInfo();

	public abstract Identify getIdentify();

	public abstract String getName();

	public abstract void join(Node nodeOnCycle);

	public abstract void updateRouteTable(Node node);

}