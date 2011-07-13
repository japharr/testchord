package com.felix.chord.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.felix.chord.EndPoint;
import com.felix.chord.Node;
import com.felix.chord.NodeImpl;
import com.felix.util.Identify;

public class LocalNodeFactory {
	private static final Map<Identify,EndPoint> nodePool=new ConcurrentHashMap<Identify, EndPoint>();
	
	
	public static Node createNode(String name){
		NodeImpl node=new NodeImpl(name);
		nodePool.put(node.getIdentify(), new LocalEndPoint(node));
		return node;
	}
	
	public static Node createNode(Identify id){
		NodeImpl node=new NodeImpl(id);
		nodePool.put(node.getIdentify(), new LocalEndPoint(node));
		return node;
	}
	
	static EndPoint getEndPoint(Identify id){
		return nodePool.get(id);
	}
	
	/**
	 * 
	 * for test only
	 */
	static void clear(){
		nodePool.clear();
	}
	
}
