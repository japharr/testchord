package com.felix.util;

import java.math.BigInteger;
import java.util.Arrays;

public class Identify implements Comparable<Identify> {
	private final byte[] id;

	// private final byte[] minValue;
	// private final byte[] maxValue;
	public Identify(byte[] id) {
		this.id = new byte[id.length];
		System.arraycopy(id, 0, this.id, 0, id.length);
		// minValue=new byte[id.length];
		// Arrays.fill(minValue, (byte)0x00);
		// maxValue=new byte[id.length];
		// Arrays.fill(maxValue, (byte)0xff);
	}

	public static Identify getIdentify(String key) {
		return new Identify(HashingTool.getHashingByName("sha-1",
				key.getBytes()));
	}

	public int getLength() {
		return id.length * 8;
	}

	@Override
	public String toString() {
		return BitConverter.byteArrayToString(id);
	}

	// public BigInteger getBigInteger(){
	// return new BigInteger(1,id);
	// }

	// public BigInteger getMaxBigInteger(){
	// return new BigInteger(1,maxValue);
	// }
	//
	// public BigInteger getMinBigInteger(){
	// return new BigInteger(1,minValue);
	// }

	@Override
	public int compareTo(Identify other) {
		if (this.id.length != other.id.length) {
			throw new ClassCastException(
					"Only Identify objects with same length can be compared!");
		}
		for (int i = 0; i < id.length; i++) {
			int a = 0xff & this.id[i];
			int b = 0xff & other.id[i];
			int dis = a - b;
			if (dis != 0)
				return dis;
		}
		return 0;
	}

	/**
	 * if(start < end) return key >start &&key<=end
	 * 
	 * 
	 * if(start > end) return key> start || key< end eg: n=64 start=62 end=3
	 * key=1 return true
	 * 
	 * if(start==end) 意味着当前环里只有一个节点 always return true
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isIdBetween(Identify key, Identify start, Identify end) {
		int cmp = end.compareTo(start);
		if (cmp > 0)
			return key.compareTo(start) > 0 && key.compareTo(end) <= 0;
		else if (cmp < 0) {
			return key.compareTo(start) > 0 || key.compareTo(end) <= 0;
		} else {
			return true;
		}
	}

	/**
	 * 距离为1 返回0 return int(log(successor − n))
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static int getDistenceLog(Identify first, Identify second) {
		if (first.equals(second)) {
			return -1;
		}
		Identify sub;
		if (first.compareTo(second) >= 0) {
			sub = subtract(first, second);
		} else {
			Identify tmp = subtract(second, first);
			byte[] b = new byte[first.id.length];
			Arrays.fill(b, (byte) 0xff);
			Identify max = new Identify(b);
			sub = subtract(max, tmp).plusPow2(0);
		}
		int disnum = 0;
		byte last = 0;
		for (byte b : sub.id) {
			if (b == 0) {
				disnum += 8;
			} else {
				last = b;
				break;
			}
		}
		if (last != 0) {
			int tmp = last & 0xff;
			// if(tmp>=128)
			// return disnum;
			if (tmp < 128) {
				disnum++;
			}
			if (tmp < 64) {
				disnum++;
			}
			if (tmp < 32) {
				disnum++;
			}
			if (tmp < 16) {
				disnum++;
			}
			if (tmp < 8) {
				disnum++;
			}
			if (tmp < 4) {
				disnum++;
			}
			if (tmp < 2) {
				disnum++;
			}
		}
		return sub.id.length * 8 - disnum - 1;
	}

	public Identify plusPow2(final int bit) {
		final int length = id.length;
		final byte[] tmp = new byte[length];
		System.arraycopy(id, 0, tmp, 0, length);
		final int bytePos = bit / 8;
		int plus = 1 << (bit % 8);
		for (int i = length - 1 - bytePos; i >= 0 && plus > 0; i--) {
			int v = 0xff & tmp[i];
			int result = v + plus;
			if (result > 255) {
				plus = 1;
				result -= 256;
			} else {
				plus = 0;
			}
			tmp[i] = (byte) (result & 0xff);
		}
		return new Identify(tmp);
	}

	public Identify subtractPow2(final int bit) {
		final int length = id.length;
		final byte[] tmp = new byte[length];
		final int bytePos = bit / 8;
		tmp[length-1-bytePos]=(byte) (0xff & (1 << (bit % 8)));
		Identify first=this;
		Identify second=new Identify(tmp);
		Identify sub;
		if (first.compareTo(second) >= 0) {
			sub = subtract(first, second);
		} else {
			Identify tt = subtract(second, first);
			byte[] b = new byte[first.id.length];
			Arrays.fill(b, (byte) 0xff);
			Identify max = new Identify(b);
			sub = subtract(max, tt).plusPow2(0);
		}
		return sub;
	}

	static Identify subtract(Identify first, Identify second) {

		if (first.compareTo(second) < 0)
			throw new IllegalArgumentException();
		final int length = first.id.length;
		if (first.equals(second)) {
			return new Identify(new byte[length]);
		}
		int jiewei = 0;
		final byte[] firstArray = first.id;
		final byte[] secondArray = second.id;
		final byte[] result = new byte[length];
		for (int i = length - 1; i >= 0; i--) {
			int f = 0xff & firstArray[i];
			f = f - jiewei;
			jiewei = 0;
			int s = 0xff & secondArray[i];
			if (f >= s) {
				result[i] = (byte) ((f - s) & 0xff);
			} else {
				jiewei++;
				result[i] = (byte) ((f + 0xff + 1 - s) & 0xff);
			}
		}
		return new Identify(result);
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
