package com.felix.chord;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.felix.util.DataEntry;
import com.felix.util.Identify;


public class NodeTest {
	@Test
	public void testJoin() {
		Node node1=new NodeImpl("node1");
		node1.join();
		assertEquals(node1,node1.getPrecessor());
		assertEquals(node1,node1.getSuccessor());
	}
	
	
	@Test
	public void testJoinNodeImpl() {
		Node node1=new NodeImpl("node1");
		node1.join();
		Node node2=new NodeImpl("node2");
		node2.join(node1);
		Node node3=new NodeImpl("node3");
		node3.join(node1);
		Node node4=new NodeImpl("node4");
		node4.join(node1);
		Node node5=new NodeImpl("node5");
		node5.join(node1);
		Node node6=new NodeImpl("node6");
		node6.join(node1);
		
		Node minNode=node1;
		Node node=node1;
		do{
			if(node.getIdentify().compareTo(minNode.getIdentify())<0){
				minNode=node;
			}
			node=node.getSuccessor();
			
		}while(node!=node1);
		node=minNode;
		do{
			Node sucNode=node.getSuccessor();
			assertEquals(node,sucNode.getPrecessor());
			if(sucNode!=minNode)
				assertTrue(node.getIdentify().compareTo(sucNode.getIdentify())<0);
			else{
				assertTrue(node.getIdentify().compareTo(sucNode.getIdentify())>0);
			}
			node=sucNode;
		}while(node!=minNode);
		((NodeImpl)node1).showDatas();
	}

	@Test
	public void testFindSuccessor() {
		Node node1=new NodeImpl("node1");
		node1.join();
		assertEquals(node1, node1.findSuccessor(node1.getIdentify()));
		Node node2=new NodeImpl("node2");
		node2.join(node1);
		assertEquals(node2, node1.findSuccessor(node2.getIdentify()));
		Node node3=new NodeImpl("node3");
		node3.join(node1);
		assertEquals(node3, node1.findSuccessor(node3.getIdentify()));
		Node node4=new NodeImpl("node4");
		node4.join(node1);
		assertEquals(node4, node1.findSuccessor(node4.getIdentify()));
		System.out.println();
		((NodeImpl)node1).showDatas();
		Node node5=new NodeImpl("node5");
		node5.join(node1);
		System.out.println();
		((NodeImpl)node1).showDatas();
		assertEquals(node5, node1.findSuccessor(node5.getIdentify()));
		Node node6=new NodeImpl("node6");
		node6.join(node1);
		assertEquals(node6, node1.findSuccessor(node6.getIdentify()));
	}
//
//	@Test
//	public void testAddData() {
//		Node node1=new NodeImpl("node1");
//		node1.join();
//		Node node2=new NodeImpl("node2");
//		node2.join(node1);
//		Node node3=new NodeImpl("node3");
//		node3.join(node1);
//		Node node4=new NodeImpl("node4");
//		node4.join(node1);
//		Node node5=new NodeImpl("node5");
//		node5.join(node1);
//		Node node6=new NodeImpl("node6");
//		node6.join(node1);
//		for(int i=0;i<30;i++)
//			node1.addData("test"+i, "data"+i);
//		Node node=node1;
//		do{
//			Map<Identify,DataEntry> map=node.getStore();
//			for(Identify id:map.keySet()){
//				assertTrue(Identify.isIdBetween(id, node.getPrecessor().getIdentify(), node.getIdentify()));
//			}
//			node=node.getSuccessor();
//		}while(node!=node1);
//		
//	}



//	@Test
//	public void testGetChargeDataWithJoin() {
//		Node node1=new NodeImpl("node1");
//		node1.join();
//		Node node2=new NodeImpl("node2");
//		node2.join(node1);
//		Node node3=new NodeImpl("node3");
//		node3.join(node1);
//		Node node4=new NodeImpl("node4");
//		node4.join(node1);
//		Node node5=new NodeImpl("node5");
//		node5.join(node1);
//		Node node6=new NodeImpl("node6");
//		node6.join(node1);
//		for(int i=0;i<30;i++)
//			node1.addData("test"+i, "data"+i);
////		for(int i=7;i<30;i++){
////			Node node=new NodeImpl("node"+i);
////			node.join(node1);
////		}
//		Node node=node1;
//		do{
//			Map<Identify,DataEntry> map=node.getStore();
//			for(Identify id:map.keySet()){
//				assertTrue(Identify.isIdBetween(id, node.getPrecessor().getIdentify(), node.getIdentify()));
//			}
//			node=node.getSuccessor();
//		}while(node!=node1);
//		node2.showDatas();
//	}


	@Test
	public void testLeaf() {
		Node node1=new NodeImpl("node1");
		node1.join();
		Node node2=new NodeImpl("node2");
		node2.join(node1);
		Node node3=new NodeImpl("node3");
		node3.join(node1);
		Node node4=new NodeImpl("node4");
		node4.join(node1);
		Node node5=new NodeImpl("node5");
		node5.join(node1);
		Node node6=new NodeImpl("node6");
		node6.join(node1);
//		for(int i=0;i<30;i++)
//			node1.addData("test"+i, "data"+i);
//		node3.leaf();
//		node4.leaf();
//		node5.leaf();
//		node6.leaf();
//		Node node=node1;
//		do{
//			Map<Identify,DataEntry> map=node.getStore();
//			for(Identify id:map.keySet()){
//				assertTrue(Identify.isIdBetween(id, node.getPrecessor().getIdentify(), node.getIdentify()));
//			}
//			node=node.getSuccessor();
//		}while(node!=node1);
	}
	
	
	@Test
	public void testfull(){
		
		Node node1=new NodeImpl(getId((byte) 0));
		node1.join();
		for(int i=1;i<=0xff;i+=1){
			Node node=new NodeImpl(getId((byte)(i&0xff)));
			node.join(node1);
//			System.out.println("================================================================");
//			((NodeImpl)node1).showDatas();
		}
		node1=null;
		node1=new NodeImpl(getId((byte)0xff));
		node1.join();
		for(int i=0xfe;i>=0;i--){
			Node node=new NodeImpl(getId((byte)(i&0xff)));
			node.join(node1);
		}
	}
	
	
	private Identify getId(byte i) {
		return new Identify(new byte[]{i});
	}


}
