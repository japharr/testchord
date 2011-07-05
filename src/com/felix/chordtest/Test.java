package com.felix.chordtest;

import com.felix.chord.Node;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node1=new Node("node1");
		node1.join();
		outputCycle(node1);
		
		Node node2=new Node("node2");
		node2.join(node1);
		outputCycle(node1);
//		
		Node node3=new Node("node3");
		node3.join(node1);
		outputCycle(node1);
		
		Node node4=new Node("node4");
		node4.join(node1);
		outputCycle(node1);
		
		Node node5=new Node("node5");
		node5.join(node1);
		outputCycle(node1);
		
		Node node6=new Node("node6");
		node6.join(node1);
		outputCycle(node1);
		
				
		for(int i=0;i<30;i++)
			node1.addData("test"+i, "data"+i);
		System.out.println("data added");
		node2.showDatas();
		for(int i=7;i<30;i++){
			Node node=new Node("node"+i);
			node.join(node1);
		}
		
		System.out.println("node added");
		node2.showDatas();
		
		node1.leaf();
		outputCycle(node2);
		node2.showDatas();
	}
	
	static void outputCycle(Node node){
		System.out.println("current cycle");
		for(String string:node.travelCircle()){
			System.out.println(string);
		}
		System.out.println();
	}

}
