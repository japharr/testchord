package com.felix.chord.local;

import java.util.List;

import com.felix.chord.EndPoint;
import com.felix.chord.NodeImpl;
import com.felix.chord.NodeInfo;
import com.felix.chord.NodeReference;

public class LocalEndPoint extends EndPoint{

	public LocalEndPoint(NodeImpl node) {
		super(node);
	}



	@Override
	protected NodeReference getReference(NodeInfo info) {
		return new LocalNodeReference(info);
	}

}
