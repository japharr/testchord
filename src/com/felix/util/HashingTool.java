package com.felix.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingTool {

	
	public static byte[] getHashingByName(String name,byte[] in){
		try {
			MessageDigest md=MessageDigest.getInstance(name);
			return md.digest(in);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
