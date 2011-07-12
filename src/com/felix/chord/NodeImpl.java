package com.felix.chord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.felix.util.Identify;

public class NodeImpl extends AbstractNode implements Node{
	private Node successor;
	private Node precessor;

//	private Map<Identify, DataEntry> store = new HashMap<Identify, DataEntry>();

	
	private final Node selfReference;
	private final Node[] routeTable;
	private final int routeTableSize;
	
	
	public NodeImpl(String name) {
		this(name, Identify.getIdentify(name));
	}
	
	public NodeImpl(Identify id){
		this(id.toString(), id);
	}

	public NodeImpl(String name,Identify id){
		super(name, id);
		this.routeTableSize=this.getIdentify().getLength();
		this.routeTable=new Node[routeTableSize];
		this.selfReference=new SelfReference(this);
	}
	
	// public Node(Identify id) {
	// this.id = id;
	// }

	public Node findSuccessor(Identify key) {
		// 没有successor 只有他一个节点
		if (successor == selfReference) {
			return this;
		}

		if (Identify.isIdBetween(key,this.getIdentify(), successor.getIdentify())) {
			return successor;
		} else {
			Node routeNode=closestPrecedingNode(key);
			return routeNode.findSuccessor(key);
		}

	}

	private Node closestPrecedingNode(Identify key) {
		for(int i=routeTableSize-1;i>=0;i--){
			Node route=routeTable[i];
			if(route!=null&&Identify.isIdBetween(route.getIdentify(), this.getIdentify(), key)&&(!route.getIdentify().equals(key))){
				return route;
			}
		}
		return this;
	}

//	public void addData(String key, Object data) {
//		Identify hashKey = Identify.getIdentify(key);
//		Node node = this.findSuccessor(hashKey);
//		node.storeData(hashKey, key, data);
//	}

//	void storeData(Identify key, String stringKey, Object data) {
//		store.put(key, new DataEntry(stringKey, data));
//	}

	// public Node closestPrecedingNode(Identify id){
	// return null;
	// }
	public Node getSuccessor() {
		return successor;
	}

	public Node getPrecessor() {
		return precessor;
	}

	public void join(Node nodeOnCycle) {
		if (this.successor != null && this.precessor != null)
			throw new IllegalStateException();
		Node s = nodeOnCycle.findSuccessor(this.getIdentify());
		
		this.successor = s;
		this.precessor = s.getPrecessor();
		s.getPrecessor().addSuccessor(this);
		s.addPrecessor(this);
		buildRouteMap(s);
		updateOthers();
		
//		store.putAll(this.successor.getChargeData(this.precessor.id,this.id));

	}

//	public Map<Identify, DataEntry> getChargeData(Identify start,Identify end) {
//		Map<Identify, DataEntry> map = new HashMap<Identify, DataEntry>();
//		for (Iterator<Entry<Identify, DataEntry>> iterator = store.entrySet()
//				.iterator(); iterator.hasNext();) {
//			Entry<Identify, DataEntry> entry = iterator.next();
//			if (Identify.isIdBetween(entry.getKey(), start, end)) {
//				map.put(entry.getKey(), entry.getValue());
//				iterator.remove();
//			}
//
//		}
//		return map;
//	}

	public void join() {
		this.successor = selfReference;
		this.precessor = selfReference;
		routeTable[routeTableSize-1]=selfReference;
	}

	public void leaf() {
		this.precessor.addSuccessor(this.successor);
		this.successor.addPrecessor(this.precessor);
//		for (Entry<Identify, DataEntry> entry : store.entrySet()) {
//			this.successor.storeData(entry.getKey(), entry.getValue().getKey(),
//					entry.getValue().getData());
//		}
	}

	
	
	
//	Map<Identify, DataEntry> getStore() {
//		return Collections.unmodifiableMap(store);
//	}

	
	void buildRouteMap(Node nodeOnCycle){
		int i=Identify.getDistenceLog(nodeOnCycle.getIdentify(), this.getIdentify());
		routeTable[i]=nodeOnCycle;
		int j=i+1;
		while(j<routeTableSize){
			Node nextRoute=nodeOnCycle.findSuccessor(this.getIdentify().plusPow2(j));
			int dis=Identify.getDistenceLog(nextRoute.getIdentify(), this.getIdentify());
			if(dis==-1)
				break;
			routeTable[dis]=nextRoute;
			j=dis+1;
		}
	}
	
	void updateOthers(){
//		for(int i=0;i<routeTableSize;i++){
//			Node route=routeTable[i];
//			if(route!=null){
//				route.updateRouteTable(this);
//			}
//		}
		Node node=this.successor;
		while(node!=this){
			node.updateRouteTable(this);
			node=node.getSuccessor();
		}
	}
	
	public void updateRouteTable(Node node) {
		int dis=Identify.getDistenceLog(node.getIdentify(), this.getIdentify());
		if(dis==-1) return;
		if(routeTable[dis]==null){
			routeTable[dis]=node;
		}
		else if(Identify.isIdBetween(node.getIdentify(), this.getIdentify(), routeTable[dis].getIdentify())){
			routeTable[dis]=node;
		}
		precessor.updateRouteTable(node);
		
	}

	public List<String> travelCircle() {
		List<String> list = new ArrayList<String>();
		Node node = this;
		do {
			list.add(node.getName() + "\t" + node.getIdentify());
			node = node.getSuccessor();
		} while (node != null && node != this);
		return list;
	}

	public void showDatas() {
		Node node = this;
		do {
			System.out.println("----" + node.getName() + " " + node.getIdentify() + "----");
			int start=0;int end=0;
			for(Node route:node.getRouteTable()){
				
				if(route==null){
					
				}else{
					System.out.println(start+"-"+end+":"+route.getName());
					start=end+1;
				}
				end++;
			}
//			for (Entry<Identify, DataEntry> entry : node.store.entrySet()) {
//				System.out.println(entry.getKey() + " "
//						+ entry.getValue().getKey() + "\t"
//						+ entry.getValue().getData());
//			}
			node = node.getSuccessor();
		} while (node != null && node != this);
	}

	@Override
	public void addPrecessor(Node precessor) {
		this.precessor=precessor;
		
	}

	@Override
	public void addSuccessor(Node successor) {
		this.successor=successor;
		
	}

	@Override
	public List<Node> getRouteTable() {
		return Collections.unmodifiableList(Arrays.asList(this.routeTable));
	}

	
	
	private static class SelfReference extends AbstractNode implements Node{

		final NodeImpl impl;
		public SelfReference(NodeImpl impl) {
			super(impl.getName(), impl.getIdentify());
			this.impl=impl;
		}

		@Override
		public void addPrecessor(Node precessor) {
			impl.addPrecessor(precessor);
		}

		@Override
		public void addSuccessor(Node successor) {
			impl.addSuccessor(successor);
			
		}

		@Override
		public Node findSuccessor(Identify key) {
			return impl.findSuccessor(key);
		}

		@Override
		public Node getPrecessor() {
			return impl.getPrecessor();
		}

		@Override
		public Node getSuccessor() {
			return impl.getSuccessor();
		}

		@Override
		public void join(Node nodeOnCycle) {
			impl.join(nodeOnCycle);
		}

		@Override
		public void updateRouteTable(Node node) {
			impl.updateRouteTable(node);
		}

		@Override
		public List<Node> getRouteTable() {
			return impl.getRouteTable();
		}
		@Override
		public void join() {
			impl.join();
		}
		
	}

}
