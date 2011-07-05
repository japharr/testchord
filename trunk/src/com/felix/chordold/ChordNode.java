package com.felix.chordold;

import java.math.BigInteger;

import com.felix.util.Identify;

public class ChordNode extends AbstractNode {
	private RemoteNode successor;
	private RemoteNode predecessor;

	private final int routeTableSize;

	private final RemoteNode[] routeTable;

	public ChordNode(Identify identify) {
		super(identify);
		routeTableSize = identify.getLength();
		routeTable = new RemoteNode[routeTableSize];
	}

	public RemoteNode findSuccessor(Identify id) {
		if (isIdBetweenThisAndSuccessor(id))
			return this.successor;
		else {
			Node rn = closestPrecedingNode(id);
			return rn.findSuccessor(id);
		}
	}

	private Node findPredecessor(Identify id) {
		Node n = this;
		while (notBetweenNodeAndSuccessor(n, id)) {
			n = n.closestPrecedingNode(id);
		}
		return n;
	}

	public Node closestPrecedingNode(Identify id) {
		for (int i = routeTableSize - 1; i >= 0; i--) {
			if (isIdBetweenThisAndSuccessor(routeTable[i].getIdentify()))
				return routeTable[i];
		}
		return this;
	}

	public RemoteNode getSuccessor() {
		return successor;
	}

	public void join(RemoteNode rn) {
		predecessor = null;
		RemoteNode node = rn.findSuccessor(identify);
		successor = node;
		buildRouteTable(node);
		

	}

	private void buildRouteTable(RemoteNode node) {
		int j=getLogDistance(node.getIdentify());
		for(int i=0;i<=j;i++){
			routeTable[j]=node;
		}
		
//		for(int i=j+1;i<identify.getLength();){
//			RemoteNode tmp=node.findSuccessor(identify+pow(2,i));
//			int dis=getLogDistance(tmp.getIdentify());
//			for(;i<=dis;i++){
//				routeTable[j]=node;
//			}
//		}
		

	}
	
	public int getLogDistance(Identify remoteId){
		 BigInteger local=identify.getBigInteger();
		 BigInteger remote=remoteId.getBigInteger();
		 if(local.compareTo(remote)>0){
			 remote=remote.add(remoteId.getMaxBigInteger());
		 }
		 BigInteger dis=remote.subtract(local).subtract(new BigInteger("1"));
		 return dis.signum()<0?remoteId.getLength()-1:dis.bitLength();
	}

	private boolean notBetweenNodeAndSuccessor(Node node, Identify id) {
		return !(id.compareTo(node.getIdentify()) > 0 && id.compareTo(node
				.getSuccessor().getIdentify()) <= 0);
	}

	private boolean isIdBetweenThisAndSuccessor(Identify id) {
		return id.compareTo(this.identify) > 0
				&& id.compareTo(successor.getIdentify()) <= 0;
	}

}
