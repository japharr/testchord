package com.felix.chordold;

import com.felix.util.Identify;

public abstract class AbstractNode implements Node{
	protected final Identify identify;
	
	public AbstractNode(Identify identify){
		this.identify = identify;
	}
	
	@Override
	public abstract RemoteNode findSuccessor(Identify id);

	@Override
	public abstract Node closestPrecedingNode(Identify id);

	@Override
	public abstract Node getSuccessor();

	@Override
	public Identify getIdentify() {

		return identify;
	}

}
