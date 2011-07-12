package com.felix.chord.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.felix.chord.Node;
import com.felix.chord.NodeImpl;
import com.felix.util.Identify;

public class LocalNodeFactory {
	private static final Map<Identify,NodeImpl> nodePool=new ConcurrentHashMap<Identify, NodeImpl>();
	
	
	public static Node createNode(String name){
		NodeImpl node=new NodeImpl(name);
		nodePool.put(node.getIdentify(), node);
		return node;
	}
	
	static NodeImpl getNode(Identify id){
		return nodePool.get(id);
	}
	
}
