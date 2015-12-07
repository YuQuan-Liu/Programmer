package com.rocket.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.RXTXCommDriver;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.Enumeration;

import com.rocket.util.Property;

public class Serial {
	public static SerialPort serialPort = null;
	
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
		String[] ports = getPortsName();
		for(int i = 0;i < ports.length;i++)
			System.out.println(ports[i]);
		
		RXTXCommDriver driver = new RXTXCommDriver();
		driver.initialize();
		try {
			serialPort = (SerialPort) driver.getCommPort("COM4", CommPortIdentifier.PORT_SERIAL);
			serialPort.setSerialPortParams(Property.getIntValue("BaudRate"), Property.getIntValue("DATABITS"), Property.getIntValue("STOPBITS"), Property.getIntValue("PARITY"));

			System.out.println(serialPort);
			System.out.println(serialPort.isReceiveTimeoutEnabled());
			System.out.println(serialPort.isReceiveThresholdEnabled());
			
			new Thread(new SerialReader(serialPort.getInputStream())).start();
			new Thread(new SerialWriter(serialPort.getOutputStream())).start();
			
			//			serialPort.enableReceiveTimeout(Property.getIntValue("TIMEOUT"));
//			serialPort.enableReceiveThreshold(1);
//			in = serialPort.getInputStream();
//			out = serialPort.getOutputStream();
			
			SerialWriter.queue_out.put("Hello".getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
