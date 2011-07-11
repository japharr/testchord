package com.felix.chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.felix.util.DataEntry;
import com.felix.util.Identify;

public class Node {
	private final Identify id;
	private final String name;
	private Node successor;
	private Node precessor;

//	private Map<Identify, DataEntry> store = new HashMap<Identify, DataEntry>();
	
	private final Node[] routeTable;
	private final int routeTableSize;
	
	public Node(String name) {
		this.name = name;
		this.id = Identify.getIdentify(name);
		this.routeTableSize=this.id.getLength();
		this.routeTable=new Node[routeTableSize];
	}
	
	public Node(Identify id){
		this.name=id.toString();
		this.id=id;
		this.routeTableSize=id.getLength();
		this.routeTable=new Node[routeTableSize];
	}

	// public Node(Identify id) {
	// this.id = id;
	// }

	public Node findSuccessor(Identify key) {
		// 没有successor 只有他一个节点
		if (successor == this) {
			return this;
		}

		if (Identify.isIdBetween(key,this.id, successor.id)) {
			return successor;
		} else {
			Node routeNode=closestPrecedingNode(key);
			return routeNode.findSuccessor(key);
		}

	}

	private Node closestPrecedingNode(Identify key) {
		for(int i=routeTableSize-1;i>=0;i--){
			Node route=routeTable[i];
			if(route!=null&&Identify.isIdBetween(route.id, this.id, key)&&(!route.id.equals(key))){
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

	public Identify getIdentify() {
		return id;
	}

	public void join(Node nodeOnCycle) {
		if (this.successor != null && this.precessor != null)
			throw new IllegalStateException();
		Node s = nodeOnCycle.findSuccessor(id);
		
		this.successor = s;
		this.precessor = s.precessor;
		s.precessor.successor = this;
		s.precessor = this;
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
		this.successor = this;
		this.precessor = this;
		routeTable[routeTableSize-1]=this;
	}

	public void leaf() {
		this.precessor.successor = this.successor;
		this.successor.precessor = this.precessor;
//		for (Entry<Identify, DataEntry> entry : store.entrySet()) {
//			this.successor.storeData(entry.getKey(), entry.getValue().getKey(),
//					entry.getValue().getData());
//		}
	}

	
	
	
//	Map<Identify, DataEntry> getStore() {
//		return Collections.unmodifiableMap(store);
//	}

	
	void buildRouteMap(Node nodeOnCycle){
		int i=Identify.getDistenceLog(nodeOnCycle.id, this.id);
		routeTable[i]=nodeOnCycle;
		int j=i+1;
		while(j<routeTableSize){
			Node nextRoute=nodeOnCycle.findSuccessor(this.id.plusPow2(j));
			int dis=Identify.getDistenceLog(nextRoute.id, this.id);
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
			node=node.successor;
		}
	}
	
	//hard to implements
	void updateRouteTable(Node node) {
		int dis=Identify.getDistenceLog(node.id, this.id);
		if(dis==-1) return;
		if(routeTable[dis]==null){
			routeTable[dis]=node;
		}
		else if(Identify.isIdBetween(node.id, this.id, routeTable[dis].id)){
			routeTable[dis]=node;
		}
		precessor.updateRouteTable(node);
		
	}

	public List<String> travelCircle() {
		List<String> list = new ArrayList<String>();
		Node node = this;
		do {
			list.add(node.name + "\t" + node.id);
			node = node.successor;
		} while (node != null && node != this);
		return list;
	}

	public void showDatas() {
		Node node = this;
		do {
			System.out.println("----" + node.name + " " + node.id + "----");
			int start=0;int end=0;
			for(Node route:node.routeTable){
				
				if(route==null){
					
				}else{
					System.out.println(start+"-"+end+":"+route.name);
					start=end+1;
				}
				end++;
			}
//			for (Entry<Identify, DataEntry> entry : node.store.entrySet()) {
//				System.out.println(entry.getKey() + " "
//						+ entry.getValue().getKey() + "\t"
//						+ entry.getValue().getData());
//			}
			node = node.successor;
		} while (node != null && node != this);
	}
}
