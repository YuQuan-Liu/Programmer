package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.rocket.programmer.window.MainWindow;
import com.rocket.programmer.window.Meter_NoEncrypt;
import com.rocket.util.StringPad;




public class ReadHalf extends SwingWorker<Void, Void> {
	
	private JTextField showNumTextField;
	
	private static OutputStream out = null;
	private static InputStream in = null;
	private static SerialPort serialPort = null;
	
	public ReadHalf(){
		super();
	}
	
	public ReadHalf(JTextField showNumTextField,OutputStream out,InputStream in,SerialPort serialPort){
		super();
		this.showNumTextField = showNumTextField;
		this.in = in;
		this.out = out;
		this.serialPort = serialPort;
	}
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub

		serialPort.enableReceiveThreshold(9);
		while(!isCancelled()){
			readhalf();
			Thread.sleep(300);
		}
		
		return null;
	}
	
	public void readhalf(){
		byte[] re = new byte[10];
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0B;
		command[3] = 0x04;
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
				showNumTextField.setText(StringPad.leftPad(Integer.toHexString(re[7]&0xFF).toUpperCase(),2) + " "+StringPad.leftPad(Integer.toHexString(re[6]&0xFF).toUpperCase(),2) + " " +StringPad.leftPad(Integer.toHexString(re[5]&0xFF).toUpperCase(),2)+ " " +StringPad.leftPad(Integer.toHexString(re[4]&0xFF).toUpperCase(),2));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
