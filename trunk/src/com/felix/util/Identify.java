package com.felix.util;

import java.math.BigInteger;
import java.util.Arrays;



public class Identify implements Comparable<Identify>{
	private final byte[] id;
//	private final byte[] minValue;
//	private final byte[] maxValue;
	public Identify(byte[] id){
		this.id=new byte[id.length];
		System.arraycopy(id, 0, this.id, 0, id.length);
//		minValue=new byte[id.length];
//		Arrays.fill(minValue, (byte)0x00);
//		maxValue=new byte[id.length];
//		Arrays.fill(maxValue, (byte)0xff);
	}
	
	public static Identify getIdentify(String key){
		return new Identify(HashingTool.getHashingByName("sha-1", key.getBytes()));
	}
	
	public int getLength(){
		return id.length*8;
	}
	
	@Override
	public String toString(){
		return BitConverter.byteArrayToString(id);
	}
	
//	public BigInteger getBigInteger(){
//		return new BigInteger(1,id);
//	}
	
//	public BigInteger getMaxBigInteger(){
//		return new BigInteger(1,maxValue); 
//	}
//	
//	public BigInteger getMinBigInteger(){
//		return new BigInteger(1,minValue); 
//	}
	
	@Override
	public int compareTo(Identify other) {
		if(this.id.length!=other.id.length){
			throw new ClassCastException("Only Identify objects with same length can be compared!");
		}
		for(int i=0;i<id.length;i++){
			int a=0xff&this.id[i];
			int b=0xff&other.id[i];
			int dis=a-b;
			if(dis!=0)
				return dis;
		}
		return 0;
	}
	
	
	/**
	 * if(start < end)
	 * 	return  key >start &&key<=end
	 * 
	 * 
	 * if(start > end)
	 * 	return  key> start || key< end
	 * eg: n=64  start=62 end=3 key=1 return true
	 * 
	 * if(start==end) 意味着当前环里只有一个节点
	 * 	always return true
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isIdBetween(Identify key,Identify start,Identify end) {
		int cmp=end.compareTo(start);
		if (cmp>0)
			return key.compareTo(start) > 0
					&& key.compareTo(end) <= 0;
		else if(cmp<0){
			return key.compareTo(start)>0||key.compareTo(end)<=0;
		}else{
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identify other = (Identify) obj;
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}
	
}
