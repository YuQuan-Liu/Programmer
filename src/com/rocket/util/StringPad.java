package com.rocket.util;

public class StringPad {
	
	/**
	 * 返回补齐过的字符串
	 * @param str  需要在左面补充的字符串
	 * @param len  需要补齐到多少位
	 * @return
	 */
	public static String leftPad(String str,int len){
		
		if(str.length() < len){
			for(int i = 0;i < (len - str.length());i++ ){
				str = "0"+str;
			}
		}
		
		return str;
	}
	
}
