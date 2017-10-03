package com.rocket.serial.task;

import gnu.io.SerialPort;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rocket.programmer.window.MainWindow;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringPad;
import com.rocket.util.StringUtil;




public class LaoHua188_ReadMeters extends SwingWorker<Void, Void> {
	
	private JTextArea showtxt;
	private HashMap<String, List<String>> cjqs_map;
	private String excelName;
	
	public LaoHua188_ReadMeters(JTextArea textArea, HashMap<String, List<String>> cjqs_map){
		super();
		this.cjqs_map = cjqs_map;
		this.showtxt = textArea;
		if(cjqs_map.entrySet().size() > 0){
			createExcel();
		}
	}
	
	//初始化Excel
	private void createExcel(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		excelName = "Log_"+dateFormat.format(cal.getTime());
		Sheet sheet = null;
		Row row = null;
		try {
			Workbook wb = new HSSFWorkbook();
			for(Entry<String, List<String>> entry : cjqs_map.entrySet()){
				
				sheet = wb.createSheet(entry.getKey());
				row = sheet.createRow(0);
				//the meter title in the first row 
				List<String> meters = entry.getValue();
				for(int j = 0;j < meters.size();j++){
					row.createCell(j).setCellValue(meters.get(j));
				}
			}
			
			sheet = wb.createSheet("error");
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("信息");
			row.createCell(1).setCellValue("时间");
			
			FileOutputStream fileOut = new FileOutputStream("D:/"+excelName+".xls");
			wb.write(fileOut);
		    fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		if(cjqs_map.entrySet().size() > 0){
			while(!isCancelled()){
				for(Entry<String, List<String>> entry : cjqs_map.entrySet()){
					
					boolean open = openCJQ(entry.getKey(), 0);
					ArrayList<String> nums = new ArrayList<String>();
					List<String> meters = entry.getValue();
					
					if(open){
						for(int j = 0;j < meters.size();j++){
							if(isCancelled()){
								return null;
							}
							String num = readNum(meters.get(j));
							showtxt.append("\r\n"+meters.get(j)+":"+num);
							nums.add(num);
						}
					}else{
						for(int j = 0;j < meters.size();j++){
							if(isCancelled()){
								return null;
							}
							nums.add("采集器未开");
						}
					}
					
					writeExcel(entry.getKey(),nums);
					
					boolean close = closeCJQ(entry.getKey(), 0);
				}
				
			}
		}else{
			showtxt.append("\r\n先打开Excel,导入数据！");
		}
		
		return null;
	}
	
	
	private void writeExcel(String cjqaddr,ArrayList<String> nums) {
		try {
            InputStream mInputStream = new FileInputStream("D:/"+excelName+".xls");
            Workbook wb = new HSSFWorkbook(mInputStream);
            mInputStream.close();
            //get the cjq sheet
            Sheet mSheet = wb.getSheet(cjqaddr);
            
            Row row = mSheet.createRow(mSheet.getLastRowNum()+1);
            
            Cell cell = null;
            
            for(int i = 0;i < nums.size();i++){
            	
            	cell = row.createCell(i);
				cell.setCellValue(nums.get(i));
				
            }
			row.createCell(nums.size()).setCellValue(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
			
            
            
            
            FileOutputStream fileOut = new FileOutputStream("D:/"+excelName+".xls");
			wb.write(fileOut);
			fileOut.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public String readNum(String maddr) {
		byte[] command = new byte[20];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		
		String[] addrs=new String[7];
		for(int i = 0;i < 7;i++){
			addrs[i] = maddr.substring(i*2, 2+i*2);
		}
		
		//addr
		command[12] = (byte) Integer.parseInt(addrs[0],16);
		command[11] = (byte) Integer.parseInt(addrs[1],16);
		command[10] = (byte) Integer.parseInt(addrs[2],16);
		command[9] = (byte) Integer.parseInt(addrs[3],16);
		command[8] = (byte) Integer.parseInt(addrs[4],16);
		command[7] = (byte) Integer.parseInt(addrs[5],16);
		command[6] = (byte) Integer.parseInt(addrs[6],16);
		
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
					
					return Integer.toHexString(meterread);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean openCJQ(String raddr,int show){
		byte[] command = new byte[21];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0xA0;
		//cjqaddr
		byte[] caddr = StringUtil.string2Byte(raddr);
		for(int i = 0;i < 6;i++){
			command[6+i] = caddr[5-i];
		}
		command[12] = 0x00;
		
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
			byte[] response = (byte[]) SerialReader.queue_in.poll(6, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				showtxt.append("\r\n"+raddr+":超时");
				return false;
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x00){
						showtxt.append("\r\n"+raddr+":开");
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean closeCJQ(String raddr,int show){
		byte[] command = new byte[21];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0xA0;
		
		//cjqaddr
		byte[] caddr = StringUtil.string2Byte(raddr);
		for(int i = 0;i < 6;i++){
			command[6+i] = caddr[5-i];
		}
		command[12] = 0x00;
		
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
			byte[] response = (byte[]) SerialReader.queue_in.poll(6, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				showtxt.append("\r\n"+raddr+":超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x02){
						showtxt.append("\r\n"+raddr+":关");
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
