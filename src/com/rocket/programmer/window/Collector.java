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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Collector extends JDialog {
	private JTextField showAddrTextField;
	private JTextField showCountTextField;
	private JTextField addrTextField;
	private JTextField countTextField;
	private JLabel label;
	private JButton writeAddrBtn;
	private JButton writeCountBtn;
	private JButton readAddrBtn;
	private JLabel label_1;
	private JButton readCountBtn;

	static int isE = 0;
	static int isD = 0;
	static int isA = 0;
	static int isData = 0;
	static int countdata = 0;
	static int dataFinish = 0;
	
	final JPanel panel = new JPanel();
	final JPanel panel_1 = new JPanel();
	private JButton writeIAPBtn;
	private JButton clearBtn;
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
		
		
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "\u663E\u793A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 606, 80);
		getContentPane().add(panel);
		
		showAddrTextField = new JTextField();
		showAddrTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		showAddrTextField.setColumns(10);
		showAddrTextField.setBounds(75, 19, 105, 41);
		panel.add(showAddrTextField);
		
		showCountTextField = new JTextField();
		showCountTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		showCountTextField.setColumns(10);
		showCountTextField.setBounds(269, 19, 105, 41);
		panel.add(showCountTextField);
		
		label = new JLabel("地址：");
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(22, 32, 43, 15);
		panel.add(label);
		
		label_1 = new JLabel("表数：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(202, 33, 57, 15);
		panel.add(label_1);
		
		clearBtn = new JButton("清空");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddrTextField.setText("");
				showCountTextField.setText("");
			}
		});
		clearBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		clearBtn.setBounds(415, 29, 93, 23);
		panel.add(clearBtn);
		
		
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 125, 606, 367);
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
				writeAddr();
				readAddr();
			}
		});
		writeAddrBtn.setBounds(48, 88, 93, 23);
		panel_1.add(writeAddrBtn);
		
		writeCountBtn = new JButton("写表数");
		writeCountBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeCount();
				readCount();
			}
		});
		writeCountBtn.setBounds(48, 138, 93, 23);
		panel_1.add(writeCountBtn);
		
		readAddrBtn = new JButton("读地址");
		readAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				readAddr();
			}
		});
		readAddrBtn.setBounds(48, 36, 93, 23);
		panel_1.add(readAddrBtn);
		
		readCountBtn = new JButton("读表数");
		readCountBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readCount();
			}
		});
		readCountBtn.setBounds(171, 36, 93, 23);
		panel_1.add(readCountBtn);
		
		writeIAPBtn = new JButton("IAP");
		writeIAPBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeIAPBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeIAP();
			}
		});
		writeIAPBtn.setBounds(48, 191, 93, 23);
		panel_1.add(writeIAPBtn);
	}
	
	public void readAddr(){
		byte[] re = new byte[20];
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
			
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(command, 0, 10);
			byte[] in = new byte[10];
			countdata = 0;
			isE = 0;
			isD = 0;
			isA = 0;
			isData = 0;
			dataFinish = 0;
			
			while(MainWindow.in.read(in) > 0){
				
				readBytesCollector(in, re,10);
				if(dataFinish == 1){
					break;
				}
				
			}
			
			re[10] = 0;
			for(int i =0;i < 10;i++){
				re[10] ^= re[i];
			}
			if(re[10] == 0 && re[3] == 0x03){
//				showAddrTextField.setText(String.valueOf(re[5]&0xFF) + " "+ String.valueOf(re[6]&0xFF));
				showAddrTextField.setText(Integer.toHexString(re[5]&0xFF).toUpperCase() + " " +Integer.toHexString(re[6]&0xFF).toUpperCase());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void readCount(){
		byte[] re = new byte[20];
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
			
			
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(command, 0, 10);
			byte[] in = new byte[10];
			countdata = 0;
			isE = 0;
			isD = 0;
			isA = 0;
			isData = 0;
			dataFinish = 0;
			
			while(MainWindow.in.read(in) > 0){
				
				readBytesCollector(in, re,10);
				if(dataFinish == 1){
					break;
				}
				
			}
			re[10] = 0;
			for(int i =0;i < 10;i++){
				re[10] ^= re[i];
			}
			if(re[10] == 0 && re[3] == 0x04){
//				showAddrTextField.setText(String.valueOf(re[5]&0xFF) + " "+ String.valueOf(re[6]&0xFF));
				showCountTextField.setText(String.valueOf(re[4]&0xFF));
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
			if(addrStr.length() != 4){
				JOptionPane.showMessageDialog(panel_1, "请输入4位数字！");
				return;
			}
			addr = Integer.parseInt(addrStr);
			if(addr >= 0 && addr <= 9999){
				
			}else{
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
			e.printStackTrace();
			return;
		}
		
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x01;
		command[4] = (byte) 0xFF;
		
		String haddr = addrStr.substring(0, 2);
		String laddr = addrStr.substring(2, 4);
		
		command[5] = (byte) Integer.parseInt(haddr, 16);
		command[6] = (byte) Integer.parseInt(laddr, 16);
		
		command[7] = (byte) 0xFF;
		command[8] = (byte) 0xFF;
		command[9] = 0x00;
		for(int i =0;i < 9;i++){
			command[9] ^= command[i];
		}
		
		try {
			
			MainWindow.serialPort.enableReceiveThreshold(10);
			MainWindow.out.write(command, 0, 10);
			
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
			
			MainWindow.serialPort.enableReceiveThreshold(10);
			MainWindow.out.write(command, 0, 10);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void writeIAP(){
		
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x05;
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
			
			MainWindow.serialPort.enableReceiveThreshold(10);
			MainWindow.out.write(command, 0, 10);
			readIAP();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void readIAP(){
		byte[] re = new byte[20];
		byte[] command = new byte[10];
		command[0] = 0x0E;
		command[1] = 0x0D;
		command[2] = 0x0A;
		command[3] = 0x06;
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
			
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(command, 0, 10);
			byte[] in = new byte[10];
			countdata = 0;
			isE = 0;
			isD = 0;
			isA = 0;
			isData = 0;
			dataFinish = 0;
			
			while(MainWindow.in.read(in) > 0){
				
				readBytesCollector(in, re,10);
				if(dataFinish == 1){
					break;
				}
				
			}
			re[10] = 0;
			for(int i =0;i < 10;i++){
				re[10] ^= re[i];
			}
			if(re[10] == 0 && re[3] == 0x06){
//				showAddrTextField.setText(String.valueOf(re[5]&0xFF) + " "+ String.valueOf(re[6]&0xFF));
				showCountTextField.setText("IAP:"+String.valueOf(re[8]&0xFF));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void readBytesCollector(byte[] in, byte[] re,int dataCount) {

		if (isData == 0) {
			
			if(isE == 0){
				if(in[0] == (byte) 0x0E){
					isE = 1;
				}
			}else{
				if(isD == 0){
					if(in[0] == (byte) 0x0D){
						isD = 1;
					}
				}else{
					if(isA == 0){
						if(in[0] == (byte) 0x0A){
							isE = 0;
							isD = 0;
							isA = 0;
							re[0] = 0x0E;
							re[1] = 0x0D;
							re[2] = 0x0A;
							countdata = 3;
							isData = 1;
						}
					}
				}
			}
		} else {
			re[countdata] = in[0];
			countdata++;
			if (countdata == dataCount) {
				dataFinish = 1;
				isData = 0;
				countdata = 0;
			}
		}
	}
}
