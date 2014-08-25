package com.rocket.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Property {
	
	private static Properties props = new Properties();
	static{
		try{
			InputStream in = new FileInputStream(System.getProperty("user.dir")+"/rocket.properties");
			props.load(in);
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getStringValue(String key){
		return props.getProperty(key);
	}
	
	public static int getIntValue(String key){
		int value = 0;
		try {
			value = Integer.parseInt(props.getProperty(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void main(String[] a){
		System.out.println(Property.getIntValue("Rocket"));
	}
	
}
