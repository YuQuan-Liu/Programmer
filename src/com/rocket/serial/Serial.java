package com.rocket.serial;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;

public class Serial {
	
	//get the available ports name
	public static String[] getPortsName(){
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portstr = new ArrayList<>();
		while(ports.hasMoreElements()){
			CommPortIdentifier com = (CommPortIdentifier)ports.nextElement();
			switch (com.getPortType()){
			case CommPortIdentifier.PORT_SERIAL:
//				System.out.println(com.getName());
				portstr.add(com.getName());
			}
		}
		String[] p = new String[portstr.size()];
		for(int i =0;i < portstr.size();i++){
			p[i] = portstr.get(i);
		}
		return p;
	}
	
	public static void main(String[] args) {
		System.out.println(getPortsName());
	}
		
}
