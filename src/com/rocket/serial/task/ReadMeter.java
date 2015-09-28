package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.rocket.programmer.window.MainWindow;
import com.rocket.programmer.window.Meter_NoEncrypt;
import com.rocket.util.StringPad;




public class ReadMeter extends SwingWorker<Void, Void> {
	
	private JTextField showNumTextField;
	
	private static OutputStream out = null;
	private static InputStream in = null;
	private static SerialPort serialPort = null;
	private static boolean national = true;
	
	public ReadMeter(){
		super();
	}
	
	public ReadMeter(JTextField showNumTextField,OutputStream out,InputStream in,SerialPort serialPort,boolean national){
		super();
		this.showNumTextField = showNumTextField;
		this.in = in;
		this.out = out;
		this.serialPort = serialPort;
		this.national = national;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		serialPort.enableReceiveThreshold(9);
		while(!isCancelled()){
			if(national){
				readmeterNational();
			}else{
				readmeter();
			}
			
		}
		
		return null;
	}
	
	private void readmeterNational() {
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
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
			
//			MainWindow.serialPort.enableReceiveTimeout(2000);
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(command, 0, 20);
			
			byte[] in = new byte[10];
			Meter_NoEncrypt.countdata = 0;
			Meter_NoEncrypt.countFE = 0;
			Meter_NoEncrypt.isData = 0;
			Meter_NoEncrypt.dataLen = 0;
			Meter_NoEncrypt.dataFinish = 0;
			while(MainWindow.in.read(in) > 0){
				
				Meter_NoEncrypt.readBytes(in,re);
				if(Meter_NoEncrypt.dataFinish == 1){
					break;
				}
			}
			//deal the data re[]
			
			if(Meter_NoEncrypt.checkSum(re)){
				
				if(re[11] == (byte)0x1F && re[12] == (byte)0x90){
					int meterread = re[16]&0xFF;
					meterread = meterread << 8;
					meterread = meterread | re[15]&0xFF;
					
//					System.out.println(Integer.toHexString(re[19]&0xFF)+Integer.toHexString(re[20]&0xFF)+Integer.toHexString(re[21]&0xFF)+Integer.toHexString(re[22]&0xFF));
					showNumTextField.setText(Integer.toHexString(meterread));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void readmeter(){
		byte[] re = new byte[10];
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0B;
		command[3] = 0x03;
		command[4] = (byte) 0xFF;
		command[5] = (byte) 0xFF;
		command[6] = (byte) 0xFF;
		command[7] = (byte) 0xFF;
		command[8] = 0x00;
		for(int i =0;i < 8;i++){
			command[8] ^= command[i];
		}
		
		try {
			
			out.write(command, 0, 9);
			
			MainWindow.serialPort.enableReceiveThreshold(1);
			byte[] inbyte = new byte[10];
			Meter_NoEncrypt.countdata = 0;
			Meter_NoEncrypt.isE = 0;
			Meter_NoEncrypt.isD = 0;
			Meter_NoEncrypt.isB = 0;
			Meter_NoEncrypt.isData = 0;
			Meter_NoEncrypt.dataFinish = 0;
			
			while(in.read(inbyte) > 0){
				
				Meter_NoEncrypt.readBytesMeter(inbyte, re, 9);
				if(Meter_NoEncrypt.dataFinish == 1){
					break;
				}
				
			}
			re[9] = 0;
			for(int i =0;i < 9;i++){
				re[9] ^= re[i];
			}
			if(re[9] == 0){
				//showAddrTextField.setText(String.valueOf(re[5]&0xFF));
//				showNumTextField.setText(Integer.toHexString(re[7]&0xFF).toUpperCase() + " "+Integer.toHexString(re[6]&0xFF).toUpperCase() + " " +Integer.toHexString(re[5]&0xFF).toUpperCase());
//				showAddrTextField.setText(String.valueOf(re[5] & 0xFF));
				showNumTextField.setText(StringPad.leftPad(Integer.toHexString(
						re[6] & 0xFF).toUpperCase(),2)
						+ " "
						+ StringPad.leftPad(Integer.toHexString(re[7] & 0xFF)
								.toUpperCase(),2));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
