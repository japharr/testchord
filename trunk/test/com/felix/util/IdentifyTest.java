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
