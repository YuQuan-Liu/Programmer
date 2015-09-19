package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rocket.obj.Frame;
import com.rocket.programmer.window.Concentrator;
import com.rocket.util.Property;
import com.rocket.util.StringUtil;

public class ReadJZQAll extends SwingWorker<Void, Void>{

	private JTextField txt_fileaddr; 
	private OutputStream out;
	private InputStream in; 
	private SerialPort serialPort;
	

	public ReadJZQAll(JTextField txt_fileaddr, OutputStream out,
			InputStream in, SerialPort serialPort) {

		this.txt_fileaddr = txt_fileaddr;
		this.in = in;
		this.out = out;
		this.serialPort = serialPort;
	}

	@Override
	protected Void doInBackground() throws Exception {

		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[11];
		framedata[0] = 0x10;
		//meteraddr
		for(int i = 0;i < 7;i++){
			framedata[1+i] = (byte) 0xFF;
		}
		
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		framedata[10] = (byte) 0x00;
		
		Frame login = new Frame(11, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_READMETER, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x04, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			out.write(login.getFrame(), 0, login.getFrame().length);
			serialPort.enableReceiveTimeout(Property.getIntValue("ReadAllTimeout"));
			byte[] inbyte = new byte[2];
			byte[] deal = new byte[256];
			boolean rcv_over = false;
			
			Sheet sheet = null;
			Row row = null;
			Workbook wb = new HSSFWorkbook();
			sheet = wb.createSheet("meterread");
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("表地址");
			row.createCell(1).setCellValue("表读数");
			int rowcount = 1;
			
			while(!rcv_over){
				Concentrator.datacount = 0;
				Concentrator.header = 0;
				Concentrator.frame_len = 0;
				Concentrator.data_len = 0;
				Concentrator.data_done = false;
				while(in.read(inbyte) > 0){
					Concentrator.readBytes(inbyte, deal);
					if(Concentrator.data_done){
						break;
					}
				}
				if(Concentrator.data_done){
					if((deal[13]&0x60) == (byte)0x60 || (deal[13]&0x60) == (byte)0x20){
						rcv_over = true;
					}
					//deal the frame
					for(int i = 0;i < (Concentrator.data_len-9-4-1)/14;i++){
						byte[] maddrbytes = new byte[7];
						for(int k = 0;k < 7;k++){
							maddrbytes[6-k] = deal[20+14*i+k];
						}
						String maddrstr = "";
						for(int k = 0;k < 7;k++){
	//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
							maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF)+" ";
						}
						String meterread = String.format("%02x", deal[20+14*i+11]&0xFF)+String.format("%02x", deal[20+14*i+10]&0xFF)+String.format("%02x", deal[20+14*i+9]&0xFF);
						System.out.println(maddrstr + ":"+meterread);
//						show = show + cjqaddrstr+"~"+maddrstr+"\r\n";
						row = sheet.createRow(rowcount++);
						row.createCell(0).setCellValue(maddrstr);
						row.createCell(1).setCellValue(meterread);
					}
				}else{
					rcv_over = true;
				}
			}
			
			String excelpath = System.getProperty("user.dir")+"\\表读数"+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime())+".xls";
			System.out.println(excelpath);
			FileOutputStream fileOut = new FileOutputStream(excelpath);
			wb.write(fileOut);
		    fileOut.close();
		    
		    txt_fileaddr.setText("导出到"+excelpath);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		serialPort.enableReceiveTimeout(Property.getIntValue("TIMEOUT"));
		Concentrator.readJZQAll = null;
		return null;
	}

}
