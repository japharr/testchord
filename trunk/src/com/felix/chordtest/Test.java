package com.felix.chordtest;

import com.felix.chord.ChordNode;
import com.felix.chord.Node;
import com.felix.chord.NodeIF;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node1=new Node("node1");
		node1.join();
		outputCycle(node1);
		
		ChordNode node2=new Node("node2");
		node2.join(node1);
		outputCycle(node1);
//		
		NodeIF node3=new Node("node3");
		node3.join(node1);
		outputCycle(node1);
		
		NodeIF node4=new Node("node4");
		node4.join(node1);
		outputCycle(node1);
		
		NodeIF node5=new Node("node5");
		node5.join(node1);
		outputCycle(node1);
		
		NodeIF node6=new Node("node6");
		node6.join(node1);
		outputCycle(node1);
		
				
		for(int i=0;i<30;i++)
			node1.addData("test"+i, "data"+i);
		System.out.println("data added");
		node2.showDatas();
		for(int i=7;i<30;i++){
			NodeIF node=new Node("node"+i);
			node.join(node1);
		}
		
		System.out.println("node added");
		node2.showDatas();
		
		node1.leaf();
		outputCycle(node2);
		node2.showDatas();
	}
	
	static void outputCycle(ChordNode node){
		System.out.println("current cycle");
		for(String string:node.travelCircle()){
			System.out.println(string);
		}
		System.out.println();
	}

}
