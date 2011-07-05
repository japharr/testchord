package com.felix.chordold;

import com.felix.util.HashingTool;
import com.felix.util.Identify;

public class RemoteNode implements Node {
	private final Identify identify;
	
	
	public RemoteNode(Identify identify) {
		this.identify=identify;
	}

	public Identify getIdentify() {
		return identify;
	}

	public RemoteNode findSuccessor(Identify id) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Node findPredecessor(Identify id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Node closestPrecedingNode(Identify id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getSuccessor() {
		// TODO Auto-generated method stub
		return null;
	}

}
