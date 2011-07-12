package com.felix.chord;

import java.util.List;

import com.felix.util.Identify;

public interface Node {

	public abstract Node findSuccessor(Identify key);

	public abstract Node getSuccessor();

	public abstract Node getPrecessor();
	
	public abstract void addSuccessor(Node successor);

	public abstract void addPrecessor(Node precessor);

	public abstract Identify getIdentify();

	public abstract void join(Node nodeOnCycle);

	public abstract void join();
	
	public abstract String getName();

	public abstract void updateRouteTable(Node node);
	
	public abstract List<Node> getRouteTable();

}