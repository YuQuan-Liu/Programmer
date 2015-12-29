package com.rocket.programmer.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingWorker;

import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.concurrent.TimeUnit;
import javax.swing.UIManager;
import java.awt.Color;

public class Collector extends JDialog {
	private JTextField addrTextField;
	private JTextField countTextField;
	private JButton writeAddrBtn;
	private JButton writeCountBtn;
	private JButton readAddrBtn;
	private JButton readCountBtn;
	final JPanel panel_1 = new JPanel();
	private JTextField txt_cjqaddr;
	private JTextField txt_meteraddr;
	private JButton button_6;
	private JButton btnExcel;
	private JTextField textField_2;
	private JButton btnExcel_1;
	private JButton btnExcel_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Collector dialog = new Collector();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Collector() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Rocket~采集器");
		setBounds(100, 100, 645, 562);
		getContentPane().setLayout(null);
		
		
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5B59", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 10, 606, 187);
		getContentPane().add(panel_1);
		
		addrTextField = new JTextField();
		addrTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		addrTextField.setColumns(10);
		addrTextField.setBounds(171, 89, 218, 21);
		panel_1.add(addrTextField);
		
		countTextField = new JTextField();
		countTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		countTextField.setColumns(10);
		countTextField.setBounds(171, 139, 218, 21);
		panel_1.add(countTextField);
		
		writeAddrBtn = new JButton("写地址");
		writeAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeAddr();
						Thread.sleep(10);
						readAddr();
						return null;
					}
				}.execute();
			}
		});
		writeAddrBtn.setBounds(48, 88, 93, 23);
		panel_1.add(writeAddrBtn);
		
		writeCountBtn = new JButton("写表数");
		writeCountBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeCount();
						Thread.sleep(10);
						readCount();
						return null;
					}
				}.execute();
			}
		});
		writeCountBtn.setBounds(48, 138, 93, 23);
		panel_1.add(writeCountBtn);
		
		readAddrBtn = new JButton("读地址");
		readAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readAddr();
						return null;
					}
				}.execute();
			}
		});
		readAddrBtn.setBounds(48, 36, 93, 23);
		panel_1.add(readAddrBtn);
		
		readCountBtn = new JButton("读表数");
		readCountBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readCount();
						return null;
					}
				}.execute();
			}
		});
		readCountBtn.setBounds(171, 36, 93, 23);
		panel_1.add(readCountBtn);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u81EA\u4E3B", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 207, 606, 317);
		getContentPane().add(panel_2);
		
		txt_cjqaddr = new JTextField();
		txt_cjqaddr.setFont(new Font("宋体", Font.PLAIN, 12));
		txt_cjqaddr.setColumns(10);
		txt_cjqaddr.setBounds(323, 37, 218, 21);
		panel_2.add(txt_cjqaddr);
		
		JButton button = new JButton("写地址");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						write188Addr();
						return null;
					}
				}.execute();
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 12));
		button.setBounds(168, 36, 93, 23);
		panel_2.add(button);
		
		JButton button_1 = new JButton("开采集器");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						openCJQ();
						return null;
					}
				}.execute();
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 12));
		button_1.setBounds(48, 79, 93, 23);
		panel_2.add(button_1);
		
		JButton btn_readaddr = new JButton("读地址");
		btn_readaddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						read188Addr();
						return null;
					}
				}.execute();
			}
		});
		btn_readaddr.setFont(new Font("宋体", Font.PLAIN, 12));
		btn_readaddr.setBounds(48, 36, 93, 23);
		panel_2.add(btn_readaddr);
		
		JButton button_3 = new JButton("关采集器");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						closeCJQ();
						return null;
					}
				}.execute();
			}
		});
		button_3.setFont(new Font("宋体", Font.PLAIN, 12));
		button_3.setBounds(168, 79, 93, 23);
		panel_2.add(button_3);
		
		JButton button_4 = new JButton("抄表");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readMeter();
						return null;
					}
				}.execute();
			}
		});
		button_4.setFont(new Font("宋体", Font.PLAIN, 12));
		button_4.setBounds(48, 118, 93, 23);
		panel_2.add(button_4);
		
		txt_meteraddr = new JTextField();
		txt_meteraddr.setFont(new Font("宋体", Font.PLAIN, 12));
		txt_meteraddr.setColumns(10);
		txt_meteraddr.setBounds(411, 119, 151, 21);
		panel_2.add(txt_meteraddr);
		
		JButton button_5 = new JButton("开阀");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						openValve();
						return null;
					}
				}.execute();
			}
		});
		button_5.setFont(new Font("宋体", Font.PLAIN, 12));
		button_5.setBounds(168, 118, 93, 23);
		panel_2.add(button_5);
		
		button_6 = new JButton("关阀");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						closeValve();
						return null;
					}
				}.execute();
			}
		});
		button_6.setFont(new Font("宋体", Font.PLAIN, 12));
		button_6.setBounds(286, 118, 93, 23);
		panel_2.add(button_6);
		
		btnExcel = new JButton("Excel抄表");
		btnExcel.setFont(new Font("宋体", Font.PLAIN, 14));
		btnExcel.setBounds(48, 222, 117, 23);
		panel_2.add(btnExcel);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("高～～～～～低");
		textField_2.setFont(new Font("宋体", Font.PLAIN, 14));
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(48, 189, 351, 23);
		panel_2.add(textField_2);
		
		btnExcel_1 = new JButton("Excel开阀");
		btnExcel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		btnExcel_1.setBounds(206, 222, 117, 23);
		panel_2.add(btnExcel_1);
		
		btnExcel_2 = new JButton("Excel关阀");
		btnExcel_2.setFont(new Font("宋体", Font.PLAIN, 14));
		btnExcel_2.setBounds(359, 222, 117, 23);
		panel_2.add(btnExcel_2);
	}
	
	public void openCJQ(){
		byte[] command = new byte[21];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0xA0;
		//addr
//		command[6] = (byte) 0xAA;
//		command[7] = (byte) 0xAA;
//		command[8] = (byte) 0xAA;
//		command[9] = (byte) 0xAA;
//		command[10] = (byte) 0xAA;
//		command[11] = (byte) 0xAA;
//		command[12] = (byte) 0xAA;
		
		//addr
		String raddr = txt_cjqaddr.getText();
		if(raddr.equals("")){
			command[6] = (byte) 0x01;
			command[7] = (byte) 0x00;
			command[8] = (byte) 0x00;
			command[9] = (byte) 0x00;
			command[10] = (byte) 0x00;
			command[11] = (byte) 0x00;
			command[12] = (byte) 0x00;
		}else{
			if(raddr.length() != 12 || !raddr.matches("[0-9fF]*")){
				JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
				return;
			}
			//cjqaddr
			byte[] caddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 6;i++){
				command[6+i] = caddr[5-i];
			}
			command[12] = 0x00;
		}
		
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x00){
						//open 
						JOptionPane.showMessageDialog(panel_1, "开");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeCJQ(){
		byte[] command = new byte[21];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0xA0;
		//addr
//		command[6] = (byte) 0xAA;
//		command[7] = (byte) 0xAA;
//		command[8] = (byte) 0xAA;
//		command[9] = (byte) 0xAA;
//		command[10] = (byte) 0xAA;
//		command[11] = (byte) 0xAA;
//		command[12] = (byte) 0xAA;
		
		//addr
		String raddr = txt_cjqaddr.getText();
		if(raddr.equals("")){
			command[6] = (byte) 0x01;
			command[7] = (byte) 0x00;
			command[8] = (byte) 0x00;
			command[9] = (byte) 0x00;
			command[10] = (byte) 0x00;
			command[11] = (byte) 0x00;
			command[12] = (byte) 0x00;
		}else{
			if(raddr.length() != 12 || !raddr.matches("[0-9fF]*")){
				JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
				return;
			}
			//cjqaddr
			byte[] caddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 6;i++){
				command[6+i] = caddr[5-i];
			}
			command[12] = 0x00;
		}
		
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x02){
						//open 
						JOptionPane.showMessageDialog(panel_1, "关");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readMeter(){
		byte[] command = new byte[20];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		//addr
		String raddr = txt_meteraddr.getText();
		if(raddr.equals("")){
			command[6] = (byte) 0xAA;
			command[7] = (byte) 0xAA;
			command[8] = (byte) 0xAA;
			command[9] = (byte) 0xAA;
			command[10] = (byte) 0xAA;
			command[11] = (byte) 0xAA;
			command[12] = (byte) 0xAA;
		}else{
			
			if(raddr.length() != 14 || !raddr.matches("[0-9]*")){
				JOptionPane.showMessageDialog(panel_1, "表地址错误！");
				return;
			}
			//meteraddr
			byte[] maddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 7;i++){
				command[6+i] = maddr[6-i];
			}
		}
		
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x1F && response[12] == (byte)0x90){
					int meterread = response[16]&0xFF;
					meterread = meterread << 8;
					meterread = meterread | response[15]&0xFF;
					
					byte st0 = response[31];
					String vstatus = "";
					if((st0 & 0x03) == 0x00){
						//open 
						vstatus = "阀门开";
					}
					if((st0 & 0x03) == 0x01 || (st0 & 0x03) == 0x02){
						//
						vstatus = "阀门关";
					}
					if((st0 & 0x03) == 0x03){
						//open 
						vstatus = "阀门异常";
					}
					
					JOptionPane.showMessageDialog(panel_1, Integer.toHexString(meterread) +"    "+ vstatus);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		String raddr = txt_meteraddr.getText();
		if(raddr.equals("")){
			command[6] = (byte) 0xAA;
			command[7] = (byte) 0xAA;
			command[8] = (byte) 0xAA;
			command[9] = (byte) 0xAA;
			command[10] = (byte) 0xAA;
			command[11] = (byte) 0xAA;
			command[12] = (byte) 0xAA;
		}else{
			if(raddr.length() != 14 || !raddr.matches("[0-9]*")){
				JOptionPane.showMessageDialog(panel_1, "表地址错误！");
				return;
			}
			//meteraddr
			byte[] maddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 7;i++){
				command[6+i] = maddr[6-i];
			}
		}
		
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x00){
						//open 
						JOptionPane.showMessageDialog(panel_1, "开");
					}
				}
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
		String raddr = txt_meteraddr.getText();
		if(raddr.equals("")){
			command[6] = (byte) 0xAA;
			command[7] = (byte) 0xAA;
			command[8] = (byte) 0xAA;
			command[9] = (byte) 0xAA;
			command[10] = (byte) 0xAA;
			command[11] = (byte) 0xAA;
			command[12] = (byte) 0xAA;
		}else{
			if(raddr.length() != 14 || !raddr.matches("[0-9]*")){
				JOptionPane.showMessageDialog(panel_1, "表地址错误！");
				return;
			}
			//meteraddr
			byte[] maddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 7;i++){
				command[6+i] = maddr[6-i];
			}
		}
		
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
					byte st = response[14];
					if((st & 0x03) == 0x01){
						//close
						JOptionPane.showMessageDialog(panel_1, "关");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void write188Addr(){
		byte[] command = new byte[27];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = (byte) 0xA0;
		//addr
		command[6] = (byte) 0xAA;
		command[7] = (byte) 0xAA;
		command[8] = (byte) 0xAA;
		command[9] = (byte) 0xAA;
		command[10] = (byte) 0xAA;
		command[11] = (byte) 0xAA;
		command[12] = (byte) 0xAA;
		
		//control
		command[13] = (byte) 0x15;
		//length
		command[14] = (byte) 0x0A;
		//data
		command[15] = (byte) 0x18;
		command[16] = (byte) 0xA0;
		//serial 
		command[17] = (byte) 0x01;
		
		String raddr = txt_cjqaddr.getText();
		if(raddr.length() != 12 || !raddr.matches("[0-9fF]*")){
			JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
			return;
		}
		if(raddr.equals("")){
			JOptionPane.showMessageDialog(panel_1, "输入地址为空！");
			return;
		}else{
			
			//cjqaddr
			byte[] caddr = StringUtil.string2Byte(raddr);
			for(int i = 0;i < 6;i++){
				command[18+i] = caddr[5-i];
			}
			command[24] = 0x00;
		}
		
		command[25] = 0x00;
		for(int i = 4;i < 25;i++){
			command[25] += command[i];
		}
		command[26] = 0x16;
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x18 && response[12] == (byte)0xA0){
					String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
					for(int i = 8;i > 1;i--){
						String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
						if(pre0.length() == 1){
							pre0 = "0"+pre0;
						}
						addr = addr + pre0 +" ";
					}
					JOptionPane.showMessageDialog(panel_1, addr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void read188Addr(){
		byte[] command = new byte[20];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = (byte)0x68;
		command[5] = (byte)0xA0;
		//addr
		command[6] = (byte) 0xAA;
		command[7] = (byte) 0xAA;
		command[8] = (byte) 0xAA;
		command[9] = (byte) 0xAA;
		command[10] = (byte) 0xAA;
		command[11] = (byte) 0xAA;
		command[12] = (byte) 0xAA;
		//control
		command[13] = (byte) 0x03;
		//length
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x0A;
		command[16] = (byte) 0x81;
		//serial 
		command[17] = (byte) 0x01;
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] += command[i];
		}
		command[19] = (byte)0x16;
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[11] == (byte)0x0A && response[12] == (byte)0x81){
					String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
					for(int i = 7;i > 1;i--){
						String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
						if(pre0.length() == 1){
							pre0 = "0"+pre0;
						}
						addr = addr + pre0 +" ";
					}
					JOptionPane.showMessageDialog(panel_1, addr);
				}
			}
							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readAddr(){
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x03;
		command[4] = (byte) 0xFF;
		command[5] = (byte) 0xFF;
		
		command[6] = (byte) 0xFF;
		command[7] = (byte) 0xFF;
		command[8] = (byte) 0xFF;
		command[9] = 0x00;
		for(int i =0;i < 9;i++){
			command[9] ^= command[i];
		}
		
		try {
			
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[3] == 0x03){
//					showAddrTextField.setText(String.valueOf(re[5]&0xFF) + " "+ String.valueOf(re[6]&0xFF));
//					showAddrTextField.setText(Integer.toHexString(response[5]&0xFF).toUpperCase() + " " +Integer.toHexString(response[6]&0xFF).toUpperCase());
					JOptionPane.showMessageDialog(panel_1, Integer.toHexString(response[5]&0xFF).toUpperCase() + " " +Integer.toHexString(response[6]&0xFF).toUpperCase());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void readCount(){
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x04;
		command[4] = (byte) 0xFF;
		command[5] = (byte) 0xFF;
		
		command[6] = (byte) 0xFF;
		command[7] = (byte) 0xFF;
		command[8] = (byte) 0xFF;
		command[9] = 0x00;
		for(int i =0;i < 9;i++){
			command[9] ^= command[i];
		}
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[3] == 0x04){
//					showAddrTextField.setText(String.valueOf(re[5]&0xFF) + " "+ String.valueOf(re[6]&0xFF));
//					showCountTextField.setText(String.valueOf(response[4]&0xFF));
					JOptionPane.showMessageDialog(panel_1, String.valueOf(response[4]&0xFF));
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void writeAddr(){
		
		int addr = 0;
		String addrStr = "";
		try {
			addrStr = addrTextField.getText();
			if(addrStr.length() != 5){
				JOptionPane.showMessageDialog(panel_1, "请输入5位数字！");
				return;
			}
			addr = Integer.parseInt(addrStr);
			if(addr >= 0 && addr <= 65535){
				
			}else{
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=65535的数字");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=65535的数字");
			e.printStackTrace();
			return;
		}
		
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x01;
		command[4] = (byte) 0xFF;
		
		
		String straddr = Integer.toHexString(addr);
		
		while(straddr.length() < 4){
			
			straddr = "0" + straddr;
		}
		
		String haddr = straddr.substring(0, 2);
		String laddr = straddr.substring(2, 4);
		
		
		command[5] = (byte) Integer.parseInt(haddr, 16);
		command[6] = (byte) Integer.parseInt(laddr, 16);
		
		command[7] = (byte) 0xFF;
		command[8] = (byte) 0xFF;
		command[9] = 0x00;
		for(int i =0;i < 9;i++){
			command[9] ^= command[i];
		}
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void writeCount(){
		int count = 0;
		String countStr = "";
		try {
			countStr = countTextField.getText();
			
			count = Integer.parseInt(countStr);
			if(count >= 0 && count <= 255){
				
			}else{
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=255的数字");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=255的数字");
			e.printStackTrace();
			return;
		}
		
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x02;
		command[4] = (byte) count;
		
		command[5] = (byte) 0xFF;
		command[6] = (byte) 0xFF;
		
		command[7] = (byte) 0xFF;
		command[8] = (byte) 0xFF;
		command[9] = 0x00;
		for(int i =0;i < 9;i++){
			command[9] ^= command[i];
		}
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(command);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
