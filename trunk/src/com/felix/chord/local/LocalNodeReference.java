package com.felix.chord.local;

import com.felix.chord.EndPoint;
import com.felix.chord.Node;
import com.felix.chord.NodeInfo;
import com.felix.chord.NodeReference;
import com.felix.util.Identify;

public class LocalNodeReference extends NodeReference implements Node{
	

	public LocalNodeReference(String name, Identify id,EndPoint impl) {
		super(name, id,impl);
	}

	public LocalNodeReference(String name, Identify id) {
		this(name, id,LocalNodeFactory.getEndPoint(id));
	}
	
	public LocalNodeReference(NodeInfo info){
		this(info.getName(), info.getId());
	}
	
//	public LocalNodeReference(Node node){
//		this(node.getNodeInfo());
//	}

	@Override
	protected NodeReference getReference(NodeInfo info) {
		return new LocalNodeReference(info);
	}
	
	
	

}
