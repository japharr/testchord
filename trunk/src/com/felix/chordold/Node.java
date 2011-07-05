package com.felix.chordold;

import com.felix.util.Identify;

public interface Node {
	public RemoteNode findSuccessor(Identify id);
	//public Node findPredecessor(Identify id);
	public Node closestPrecedingNode(Identify id);
	public Node getSuccessor();
	public Identify getIdentify();
	
}
