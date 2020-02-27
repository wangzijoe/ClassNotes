package com.example.jar;

public class TestInteger {

	public static void main(String[] args) {
		System.err.println(new Integer(0) == 0);
		System.err.println(Integer.valueOf(0) == 0);
		System.err.println(Integer.valueOf(0) == Integer.valueOf(0));
		System.err.println(new Integer(0) == new Integer(0));
		System.err.println(Integer.valueOf(0) == new Integer(0));

		/**
		 * java 分隔函数split("",-1)的用途 1.如果字符串最后一位有值，则没有区别，
		 * 
		 * 2.若干最后n位都是切割符，split(" ")不会继续切分，split(" ", -1)会继续切分
		 */
		String str = ",,190510,900000001,10000,,100001,備考,,";
		
		String[] split = str.split(",");
		for (int i = 0; i < split.length; i++) {
			System.out.println(i + "=" + split[i]);
		}
		System.err.println("================================================");
		String[] items = str.split(",", -1);

		for (int i = 0; i < items.length; i++) {
			System.out.println(i + "=" + items[i]);
		}
	}
}
