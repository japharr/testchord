package com.felix.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class BitConverterTest {
	@Test
	public void testByteStringTrans(){
		for(int i=0;i<100;i++){
			byte[] data=HashingTool.getHashingByName("sha-1", (i+"123123").getBytes());
			assertArrayEquals(data, BitConverter.StringToByteArray(BitConverter.byteArrayToString(data)));
		}
	}
}
