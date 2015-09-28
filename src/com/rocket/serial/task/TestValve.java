package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.SwingWorker;

import com.rocket.programmer.window.MainWindow;
import com.rocket.programmer.window.Meter_NoEncrypt;
import com.rocket.util.Property;

public class TestValve extends SwingWorker<Void, Void> {

	
	private static OutputStream out = null;
	private static InputStream in = null;
	private static SerialPort serialPort = null;
	
	public TestValve(OutputStream out, InputStream in, SerialPort serialPort) {
		this.in = in;
		this.out = out;
		this.serialPort = serialPort;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		while(!isCancelled()){
			openValve();
			closeValve();
		}
		
		return null;
	}

	protected void openValve() {
		//national
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0x10;
		//addr
		command[6] = (byte) 0xAA;
		command[7] = (byte) 0xAA;
		command[8] = (byte) 0xAA;
		command[9] = (byte) 0xAA;
		command[10] = (byte) 0xAA;
		command[11] = (byte) 0xAA;
		command[12] = (byte) 0xAA;
		//control
		command[13] = (byte) 0x04;
		//length
		command[14] = (byte) 0x04;
		//data
		command[15] = (byte) 0x17;
		command[16] = (byte) 0xA0;
		//serial 
		command[17] = (byte) 0x01;
		//open valve
		command[18] = 0x55;
		command[19] = 0x00;
		for(int i = 4;i < 19;i++){
			command[19] += command[i];
		}
		command[20] = (byte)0x16;
		
		try {
			
//			MainWindow.serialPort.enableReceiveTimeout(2000);
			serialPort.enableReceiveThreshold(1);
			out.write(command, 0, 21);
			
			serialPort.enableReceiveTimeout(10000);
			byte[] inbytes = new byte[10];
			Meter_NoEncrypt.countdata = 0;
			Meter_NoEncrypt.countFE = 0;
			Meter_NoEncrypt.isData = 0;
			Meter_NoEncrypt.dataLen = 0;
			Meter_NoEncrypt.dataFinish = 0;
			while(in.read(inbytes) > 0){
				Meter_NoEncrypt.readBytes(inbytes,re);
				if(Meter_NoEncrypt.dataFinish == 1){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void closeValve() {
		//national
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0x10;
		//addr
		command[6] = (byte) 0xAA;
		command[7] = (byte) 0xAA;
		command[8] = (byte) 0xAA;
		command[9] = (byte) 0xAA;
		command[10] = (byte) 0xAA;
		command[11] = (byte) 0xAA;
		command[12] = (byte) 0xAA;
		//control
		command[13] = (byte) 0x04;
		//length
		command[14] = (byte) 0x04;
		//data
		command[15] = (byte) 0x17;
		command[16] = (byte) 0xA0;
		//serial 
		command[17] = (byte) 0x01;
		//open valve
		command[18] = (byte) 0x99;
		command[19] = 0x00;
		for(int i = 4;i < 19;i++){
			command[19] += command[i];
		}
		command[20] = (byte)0x16;
		
		try {
			
//			MainWindow.serialPort.enableReceiveTimeout(2000);
			serialPort.enableReceiveThreshold(1);
			out.write(command, 0, 21);
			
			serialPort.enableReceiveTimeout(10000);
			byte[] inbytes = new byte[10];
			Meter_NoEncrypt.countdata = 0;
			Meter_NoEncrypt.countFE = 0;
			Meter_NoEncrypt.isData = 0;
			Meter_NoEncrypt.dataLen = 0;
			Meter_NoEncrypt.dataFinish = 0;
			while(in.read(inbytes) > 0){
				Meter_NoEncrypt.readBytes(inbytes,re);
				if(Meter_NoEncrypt.dataFinish == 1){
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
