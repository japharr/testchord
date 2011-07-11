package com.felix.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class IdentifyTest {
	@Test
	public void testCompare() {
		Identify i1=getId(100);
		Identify i2=getId(200);
		assertTrue(i1.compareTo(i2)<0);
		assertTrue(i2.compareTo(i1)>0);
		assertTrue(i1.compareTo(i1)==0);
		assertTrue(i2.compareTo(i2)==0);
		assertTrue(i1.compareTo(getId(100))==0);
	}
	
	@Test
	public void testIsBetween(){
		assertTrue(Identify.isIdBetween(getId(100), getId(50), getId(150)));
		assertTrue(Identify.isIdBetween(getId(100), getId(50), getId(100)));
		assertFalse(Identify.isIdBetween(getId(10), getId(50), getId(150)));
		assertFalse(Identify.isIdBetween(getId(200), getId(50), getId(150)));
		
		assertFalse(Identify.isIdBetween(getId(50), getId(50), getId(150)));
		assertTrue(Identify.isIdBetween(getId(50), getId(100), getId(50)));
		assertTrue(Identify.isIdBetween(getId(120), getId(100), getId(50)));
		assertTrue(Identify.isIdBetween(getId(0), getId(100), getId(50)));
		
		assertTrue(Identify.isIdBetween(getId(50), getId(50), getId(50)));
		assertTrue(Identify.isIdBetween(getId(100), getId(50), getId(50)));
		assertTrue(Identify.isIdBetween(getId(120), getId(50), getId(50)));
	}

	@Test
	public void testSubtract(){
//		for(int i=0;i<=Integer.MAX_VALUE;i++){
//			for(int j=i;j<=Integer.MAX_VALUE;j++){
//				assertEquals(getId(j-i), Identify.subtract(getId(j),getId(i)));
//			}
//		}
		
		assertEquals(getId(50), Identify.subtract(getId(100),getId(50)));
		assertEquals(getId(0), Identify.subtract(getId(100),getId(100)));
		assertEquals(getId(1), Identify.subtract(getId(256),getId(255)));
		assertEquals(getId(2), Identify.subtract(getId(257),getId(255)));
		assertEquals(getId(64771), Identify.subtract(getId(65026),getId(255)));
		assertEquals(getId(64770), Identify.subtract(getId(65025),getId(255)));
	}
	
	@Test
	public void testGetDistenceLog(){
		assertEquals(-1,Identify.getDistenceLog(getId(100), getId(100)));
		for(int i=0;i<32;i++){
			int j=1<<i;
			assertEquals(i,Identify.getDistenceLog(getId(j), getId(0)));
		}
		assertEquals(31,Identify.getDistenceLog(getId(0), getId(1<<31)));
		int tmp=0;
		for(int i=31;i>=0;i--){
			tmp=tmp|1<<i;
			assertEquals(i,Identify.getDistenceLog(getId(0), getId(tmp)));
		}
		assertEquals(0,Identify.getDistenceLog(getId(101), getId(100)));
		for(int k=-1;k>=100;k--){
			for(int i=0;i<32;i++){
				int j=1<<i;
				assertEquals(i,Identify.getDistenceLog(getId(k+j), getId(k)));
			}
		}
		
	}
	
	@Test
	public void testPlusPow2(){
		int k=0;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
		k=1;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
		k=-1;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
		k=-100;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
		k=Integer.MAX_VALUE;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
		k=Integer.MIN_VALUE;
		for(int i=0;i<32;i++){
			assertEquals(getId(k+(1<<i)),getId(k).plusPow2(i));
		}
	}
	
	
	@Test
	public void testSubtractPow2(){
		int k=-1;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
		k=-2;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
		k=0;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
		k=101;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
		k=Integer.MAX_VALUE;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
		k=Integer.MIN_VALUE;
		for(int i=0;i<32;i++){
			assertEquals(getId(k-(1<<i)),getId(k).subtractPow2(i));
		}
	}
	
	private Identify getId(int i) {
		return new Identify(getbytes(i));
	}

	private byte[] getbytes(int num) {
		byte[] result = new byte[4];
		result[0] = (byte) ((num >> 24) & 0xFF);
		result[1] = (byte) ((num >> 16) & 0xFF);
		result[2] = (byte) ((num >> 8) & 0xFF);
		result[3] = (byte) (num & 0xFF);
		return result;
	}
}
