package com.rocket.serial.task;

import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;

import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringUtil;

public class TestValve extends SwingWorker<Void, Void> {

	public TestValve() {
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
		byte[] command = new byte[21];
		
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(20, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void closeValve() {
		//national
		byte[] command = new byte[21];
		
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(20, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
