package com.rocket.serial.task;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringPad;
import com.rocket.util.StringUtil;

public class ReadHalf extends SwingWorker<Void, Void> {
	
	private JTextField showNumTextField;
	private static boolean national = true;
	private static boolean di1 = false;
	
	public ReadHalf(){
		super();
	}
	
	public ReadHalf(JTextField showNumTextField,boolean national,boolean di1){
		super();
		this.showNumTextField = showNumTextField;
		this.national = national;
		this.di1 = di1;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		while(!isCancelled()){
			if(national){
				readhalfNational();
			}else{
				readhalf();
			}
		}
		
		return null;
	}
	
	private void readhalfNational() {
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
		if(di1){
			command[15] = (byte) 0x90;
			command[16] = (byte) 0x1F;
		}
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
				if(di1){
					if(response[12] == (byte)0x1F && response[11] == (byte)0x90){
						int meterread = response[16]&0xFF;
						meterread = meterread << 8;
						meterread = meterread | response[15]&0xFF;
						
						String half = Integer.toHexString(response[22]&0xFF)+"-"+Integer.toHexString(response[21]&0xFF)+"-"+Integer.toHexString(response[20]&0xFF)+"-"+Integer.toHexString(response[19]&0xFF);
						
						showNumTextField.setText(Integer.toHexString(meterread) +"----"+ half);
					}
				}else{
					if(response[11] == (byte)0x1F && response[12] == (byte)0x90){
						int meterread = response[16]&0xFF;
						meterread = meterread << 8;
						meterread = meterread | response[15]&0xFF;
						
						String half = Integer.toHexString(response[22]&0xFF)+"-"+Integer.toHexString(response[21]&0xFF)+"-"+Integer.toHexString(response[20]&0xFF)+"-"+Integer.toHexString(response[19]&0xFF);
						
						showNumTextField.setText(Integer.toHexString(meterread) +"----"+ half);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void readhalf(){
		byte[] command = new byte[9];
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				showNumTextField.setText(StringPad.leftPad(Integer.toHexString(response[4]&0xFF).toUpperCase(),2)+" "
				+StringPad.leftPad(Integer.toHexString(response[7]&0xFF).toUpperCase(),2) + " "
				+StringPad.leftPad(Integer.toHexString(response[6]&0xFF).toUpperCase(),2) + " " 
				+StringPad.leftPad(Integer.toHexString(response[5]&0xFF).toUpperCase(),2));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
