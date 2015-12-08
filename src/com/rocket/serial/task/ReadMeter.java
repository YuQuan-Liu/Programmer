package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.rocket.programmer.window.MainWindow;
import com.rocket.programmer.window.Meter_NoEncrypt;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringPad;
import com.rocket.util.StringUtil;




public class ReadMeter extends SwingWorker<Void, Void> {
	
	private JTextField showNumTextField;
	private static boolean national = true;
	
	public ReadMeter(){
		super();
	}
	
	public ReadMeter(JTextField showNumTextField,boolean national){
		super();
		this.showNumTextField = showNumTextField;
		this.national = national;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		while(!isCancelled()){
			readNum();
		}
		
		return null;
	}
	public void readNum() {
		if (national) {
			// national
			byte[] command = new byte[20];
			
			command[0] = (byte) 0xFE;
			command[1] = (byte) 0xFE;
			command[2] = (byte) 0xFE;
			command[3] = (byte) 0xFE;
			
			command[4] = 0x68;
			command[5] = 0x10;
			//addr
			command[6] = (byte) 0xAA;
			command[7] = (byte) 0xAA;
			command[8] = (byte) 0xAA;
			command[9] = (byte) 0xAA;
			command[10] = (byte) 0xAA;
			command[11] = (byte) 0xAA;
			command[12] = (byte) 0xAA;
			
			//control
			command[13] = (byte) 0x01;
			//length
			command[14] = (byte) 0x03;
			//data
			command[15] = (byte) 0x1F;
			command[16] = (byte) 0x90;
			//serial 
			command[17] = (byte) 0x01;
			
			command[18] = 0x00;
			for(int i = 4;i < 18;i++){
				command[18] += command[i];
			}
			command[19] = 0x16;
			
			try {
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(command);
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if(response[11] == (byte)0x1F && response[12] == (byte)0x90){
						int meterread = response[16]&0xFF;
						meterread = meterread << 8;
						meterread = meterread | response[15]&0xFF;
						
						showNumTextField.setText(Integer.toHexString(meterread));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// sun
			byte[] command = new byte[9];
			command[0] = 0x0E;
			command[1] = 0x0D;
			command[2] = 0x0B;
			
			command[3] = 0x03;
			command[4] = (byte) 0xFF;
			
			command[5] = (byte) 0xFF;
			command[6] = (byte) 0xFF;
			command[7] = (byte) 0xFF;
			command[8] = 0x00;
			for (int i = 0; i < 8; i++) {
				command[8] ^= command[i];
			}

			try {
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(command);
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//					showAddrTextField.setText(String.valueOf(response[5] & 0xFF));
					showNumTextField.setText(StringPad.leftPad(Integer.toHexString(
							response[6] & 0xFF).toUpperCase(),2)
							+ " "
							+ StringPad.leftPad(Integer.toHexString(response[7] & 0xFF)
									.toUpperCase(),2));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
