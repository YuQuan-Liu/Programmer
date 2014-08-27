package com.rocket.programmer.window;

import gnu.io.UnsupportedCommOperationException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import com.rocket.util.Property;
import com.rocket.serial.task.ReadHalf;

import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Meter_NoEncrypt extends JDialog {
	private JTextField showAddrTextField;
	private JTextField showNumTextField;
	private JTextField addrTextField;
	private JTextField firstNumTextField;

	final JCheckBox nationalCheckBox = new JCheckBox("国家推荐协议");
	final JPanel panel = new JPanel();
	final JLabel label = new JLabel("地址：");
	final JLabel label_1 = new JLabel("读数：");
	final JPanel panel_1 = new JPanel();
	final JButton readAddrBtn = new JButton("读表地址");
	final JButton readNumBtn = new JButton("读表数");
	final JButton readHalfBtn = new JButton("读半位");
	final JButton writeAddrBtn = new JButton("写地址");
	final JButton writeFirstNumBtn = new JButton("写初值");
	final JButton writeOutBtn = new JButton("出厂");
	final JButton toNationalBtn = new JButton("转为国家");
	final JButton toSunBtn = new JButton("转为海大");
	final JButton badGayBtn = new JButton("BAD MAN");
	final JButton goodManBtn = new JButton("GOOD MAN");

	public static int countdata = 0;
	public static int countFE = 0;
	public static int isData = 0;
	public static int dataLen = 0;
	public static int dataFinish = 0;
	private JTextField readNumTextField;
	
	public static int isE = 0;
	public static int isD = 0;
	public static int isB = 0;
	
	static byte[] key = new byte[8];
	private final JButton writeIAPBtn = new JButton("IAP");
	private final JButton clearBtn = new JButton("清空");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Meter_NoEncrypt dialog = new Meter_NoEncrypt();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Meter_NoEncrypt() {
		setResizable(false);
		getContentPane().setFont(new Font("宋体", Font.PLAIN, 12));
		setFont(new Font("宋体", Font.PLAIN, 12));
		setTitle("Rocket～表");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 642, 579);
		getContentPane().setLayout(null);

		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "\u663E\u793A",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 606, 80);
		getContentPane().add(panel);

		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(10, 32, 55, 15);
		panel.add(label);

		showAddrTextField = new JTextField();
		showAddrTextField.setToolTipText("高～～～～～低");
		showAddrTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		showAddrTextField.setColumns(10);
		showAddrTextField.setBounds(75, 19, 198, 41);
		panel.add(showAddrTextField);

		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(292, 32, 49, 15);
		panel.add(label_1);

		showNumTextField = new JTextField();
		showNumTextField.setBackground(new Color(255, 255, 255));
		showNumTextField.setForeground(new Color(0, 0, 0));
		showNumTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		showNumTextField.setColumns(10);
		showNumTextField.setBounds(351, 19, 105, 41);
		panel.add(showNumTextField);

		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 125, 606, 416);
		getContentPane().add(panel_1);

		readAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readAddr();
			}
		});
		readAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readAddrBtn.setBounds(46, 32, 93, 23);
		panel_1.add(readAddrBtn);
		readNumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				readNum();
			}
		});

		readNumBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readNumBtn.setBounds(46, 91, 93, 23);
		panel_1.add(readNumBtn);

		final ReadHalf readHalf = new ReadHalf(showNumTextField,
				MainWindow.out, MainWindow.in, MainWindow.serialPort);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddrTextField.setText("");
				showNumTextField.setText("");
				addrTextField.setText("");
				firstNumTextField.setText("");
				readNumTextField.setText("");
			}
		});
		clearBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		clearBtn.setBounds(479, 28, 93, 23);
		
		panel.add(clearBtn);
		readHalfBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (readHalfBtn.getText().equalsIgnoreCase("读半位")) {
					readHalfBtn.setText("停止");
					readHalf.execute();
				} else {
					readHalfBtn.setText("读半位");
					readHalf.cancel(true);
				}

			}
		});

		readHalfBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readHalfBtn.setBounds(185, 32, 93, 23);
		panel_1.add(readHalfBtn);
		writeAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeAddr();
			}
		});

		writeAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeAddrBtn.setBounds(47, 150, 93, 23);
		panel_1.add(writeAddrBtn);

		addrTextField = new JTextField();
		addrTextField.setToolTipText("高～～～～～低");
		addrTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		addrTextField.setColumns(10);
		addrTextField.setBounds(185, 151, 218, 21);
		panel_1.add(addrTextField);
		writeFirstNumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeFirst();
			}
		});

		writeFirstNumBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeFirstNumBtn.setBounds(47, 205, 93, 23);
		panel_1.add(writeFirstNumBtn);

		firstNumTextField = new JTextField();
		firstNumTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		firstNumTextField.setColumns(10);
		firstNumTextField.setBounds(185, 206, 218, 21);
		panel_1.add(firstNumTextField);
		writeOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeOut();
			}
		});

		writeOutBtn.setEnabled(false);
		writeOutBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeOutBtn.setBounds(46, 315, 93, 23);
		panel_1.add(writeOutBtn);

		toNationalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				writeToNational();
			}
		});
		toNationalBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toNationalBtn.setBounds(46, 260, 93, 23);
		panel_1.add(toNationalBtn);
		toSunBtn.setEnabled(false);

		toSunBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToHD();
				// nationalCheckBox.
			}
		});
		toSunBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toSunBtn.setBounds(185, 260, 93, 23);
		panel_1.add(toSunBtn);
		badGayBtn.setEnabled(false);
		badGayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeBad();
			}
		});

		badGayBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		badGayBtn.setBounds(324, 315, 93, 23);
		panel_1.add(badGayBtn);
		goodManBtn.setEnabled(false);
		goodManBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeGood();
			}
		});

		goodManBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		goodManBtn.setBounds(463, 315, 93, 23);
		panel_1.add(goodManBtn);
		
		readNumTextField = new JTextField();
		readNumTextField.setToolTipText("只抄单个表的时候空着，多个表时要填入表的地址");
		readNumTextField.setBounds(185, 92, 218, 21);
		panel_1.add(readNumTextField);
		readNumTextField.setColumns(10);
		writeIAPBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeIAP();
			}
		});
		writeIAPBtn.setEnabled(false);
		writeIAPBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeIAPBtn.setBounds(185, 315, 93, 23);
		
		panel_1.add(writeIAPBtn);

		nationalCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (nationalCheckBox.isSelected()) {
					writeOutBtn.setEnabled(true);
					writeIAPBtn.setEnabled(true);
					readHalfBtn.setEnabled(false);
					goodManBtn.setEnabled(true);
					badGayBtn.setEnabled(true);
					toNationalBtn.setEnabled(false);
					toSunBtn.setEnabled(true);
				} else {
					writeOutBtn.setEnabled(false);
					writeIAPBtn.setEnabled(false);
					readHalfBtn.setEnabled(true);
					goodManBtn.setEnabled(false);
					badGayBtn.setEnabled(false);
					toNationalBtn.setEnabled(true);
					toSunBtn.setEnabled(false);
				}
			}
		});
		nationalCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
		nationalCheckBox.setBounds(6, 96, 103, 23);
		getContentPane().add(nationalCheckBox);

		badGayBtn.setVisible(false);
		goodManBtn.setVisible(false);
		readHalfBtn.setVisible(false);

		if (Property.getStringValue("GOOD") != null
				&& Property.getStringValue("GOOD").equalsIgnoreCase("good")) {
			badGayBtn.setVisible(true);
			goodManBtn.setVisible(true);

		}

		if (Property.getStringValue("HALF") != null
				&& Property.getStringValue("HALF").equalsIgnoreCase("half")) {
			readHalfBtn.setVisible(true);

		}
	}

	public void readAddr(){
		if(nationalCheckBox.isSelected()){
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
				
//				MainWindow.serialPort.enableReceiveTimeout(2000);
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 20);
				
				byte[] in = new byte[10];
				countdata = 0;
				countFE = 0;
				isData = 0;
				dataLen = 0;
				dataFinish = 0;
				while(MainWindow.in.read(in) > 0){
					
					readBytes(in,re);
					if(dataFinish == 1){
						break;
					}
				}
				//deal the data re[]
				
				if(checkSum(re)){
					if(re[11] == (byte)0x0A && re[12] == (byte)0x81){
						String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
						for(int i = 8;i > 1;i--){
							String pre0 = Integer.toHexString(re[i]&0xFF).toUpperCase();
							if(pre0.length() == 1){
								pre0 = "0"+pre0;
							}
//							System.out.println(pre0);
							addr = addr + pre0 +" ";
						}
						showAddrTextField.setText(addr);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			//sun
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
				
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 9);
				byte[] in = new byte[10];
				countdata = 0;
				isE = 0;
				isD = 0;
				isB = 0;
				isData = 0;
				dataFinish = 0;
				
				while(MainWindow.in.read(in) > 0){
					
					readBytesMeter(in, re, 9);
					if(dataFinish == 1){
						break;
					}
					
				}
				
				re[9] = 0;
				for(int i =0;i < 9;i++){
					re[9] ^= re[i];
				}
				if(re[9] == 0){
					showAddrTextField.setText(String.valueOf(re[5]&0xFF));
					showNumTextField.setText(Integer.toHexString(re[6]&0xFF).toUpperCase() + " " +Integer.toHexString(re[7]&0xFF).toUpperCase());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}

	public void readNum() {
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] re = new byte[40];
			byte[] command = new byte[40];
			
			command[0] = (byte) 0xFE;
			command[1] = (byte) 0xFE;
			command[2] = (byte) 0xFE;
			command[3] = (byte) 0xFE;
			
			command[4] = 0x68;
			command[5] = 0x10;
			//addr
			String raddr = readNumTextField.getText();
			if(raddr.equals("")){
				command[6] = (byte) 0xAA;
				command[7] = (byte) 0xAA;
				command[8] = (byte) 0xAA;
				command[9] = (byte) 0xAA;
				command[10] = (byte) 0xAA;
				command[11] = (byte) 0xAA;
				command[12] = (byte) 0xAA;
			}else{
				String[] addrs = raddr.split(" ");
				if(addrs.length != 7){
					JOptionPane.showMessageDialog(panel_1, "输入地址长度不对！");
					return;
				}else{
					for(int i = 0;i < 7;i++){
						try {
							if(Integer.parseInt(addrs[i],16) >= 0 && Integer.parseInt(addrs[i],16) <= 255){
								
							}else{
								JOptionPane.showMessageDialog(panel_1, "输入地址不对！");
								return;
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(panel_1, "输入地址不对！");
							e.printStackTrace();
							return;
						}
					}
				}
//				command[6] = (byte) Integer.parseInt(addrs[0],16);
//				command[7] = (byte) Integer.parseInt(addrs[1],16);
//				command[8] = (byte) Integer.parseInt(addrs[2],16);
//				command[9] = (byte) Integer.parseInt(addrs[3],16);
//				command[10] = (byte) Integer.parseInt(addrs[4],16);
//				command[11] = (byte) Integer.parseInt(addrs[5],16);
//				command[12] = (byte) Integer.parseInt(addrs[6],16);
				
				command[12] = (byte) Integer.parseInt(addrs[0],16);
				command[11] = (byte) Integer.parseInt(addrs[1],16);
				command[10] = (byte) Integer.parseInt(addrs[2],16);
				command[9] = (byte) Integer.parseInt(addrs[3],16);
				command[8] = (byte) Integer.parseInt(addrs[4],16);
				command[7] = (byte) Integer.parseInt(addrs[5],16);
				command[6] = (byte) Integer.parseInt(addrs[6],16);
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
				
//				MainWindow.serialPort.enableReceiveTimeout(2000);
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 20);
				
				byte[] in = new byte[10];
				countdata = 0;
				countFE = 0;
				isData = 0;
				dataLen = 0;
				dataFinish = 0;
				while(MainWindow.in.read(in) > 0){
					
					readBytes(in,re);
					if(dataFinish == 1){
						break;
					}
				}
				//deal the data re[]
				
				if(checkSum(re)){
					
					if(re[11] == (byte)0x1F && re[12] == (byte)0x90){
						int meterread = re[16]&0xFF;
						meterread = meterread << 8;
						meterread = meterread | re[15]&0xFF;
						
						showNumTextField.setText(Integer.toHexString(meterread));
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// sun
			byte[] re = new byte[10];
			byte[] command = new byte[10];
			command[0] = 0x0E;
			command[1] = 0x0D;
			command[2] = 0x0B;
			
			String addr = readNumTextField.getText();
			if(addr.equals("")){
				command[3] = 0x03;
				command[4] = (byte) 0xFF;
			}else{
				command[3] = 0x02;
				int addrInt = Integer.parseInt(addr);
				if (addrInt >= 0 && addrInt <= 255) {
					command[4] = (byte) addrInt;
				} else {
					JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=255的数字");
					return;
				}
			}
			
			command[5] = (byte) 0xFF;
			command[6] = (byte) 0xFF;
			command[7] = (byte) 0xFF;
			command[8] = 0x00;
			for (int i = 0; i < 8; i++) {
				command[8] ^= command[i];
			}

			try {
				
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 9);
				byte[] in = new byte[10];
				countdata = 0;
				isE = 0;
				isD = 0;
				isB = 0;
				isData = 0;
				dataFinish = 0;
				
				while(MainWindow.in.read(in) > 0){
					
					readBytesMeter(in, re, 9);
					if(dataFinish == 1){
						break;
					}
					
				}
				
				re[9] = 0;
				for (int i = 0; i < 9; i++) {
					re[9] ^= re[i];
				}
				if (re[9] == 0) {
					
					if(addr.equals("")){
						showAddrTextField.setText(String.valueOf(re[5] & 0xFF));
						showNumTextField.setText(Integer.toHexString(
								re[6] & 0xFF).toUpperCase()
								+ " "
								+ Integer.toHexString(re[7] & 0xFF)
										.toUpperCase());
					}else{
						showAddrTextField.setText(String.valueOf(re[4] & 0xFF));
						showNumTextField.setText(Integer.toHexString(
								re[6] & 0xFF).toUpperCase()
								+ " "
								+ Integer.toHexString(re[7] & 0xFF)
										.toUpperCase());
					}
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void writeAddr() {
		if (nationalCheckBox.isSelected()) {
			// national
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
			command[13] = (byte) 0x15;
			//length
			command[14] = (byte) 0x0A;
			//data
			command[15] = (byte) 0x18;
			command[16] = (byte) 0xA0;
			//serial 
			command[17] = (byte) 0x01;
			
			String raddr = addrTextField.getText();
			if(raddr.equals("")){
				JOptionPane.showMessageDialog(panel_1, "输入地址为空！");
				return;
			}else{
				String[] addrs = raddr.split(" ");
				if(addrs.length != 7){
					JOptionPane.showMessageDialog(panel_1, "输入地址长度不对！");
					return;
				}else{
					for(int i = 0;i < 7;i++){
						try {
							if(Integer.parseInt(addrs[i],16) >= 0 && Integer.parseInt(addrs[i],16) <= 255){
								
							}else{
								JOptionPane.showMessageDialog(panel_1, "输入地址不对！");
								return;
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(panel_1, "输入地址不对！");
							e.printStackTrace();
							return;
						}
					}
				}
//				command[18] = (byte) Integer.parseInt(addrs[0],16);
//				command[19] = (byte) Integer.parseInt(addrs[1],16);
//				command[20] = (byte) Integer.parseInt(addrs[2],16);
//				command[21] = (byte) Integer.parseInt(addrs[3],16);
//				command[22] = (byte) Integer.parseInt(addrs[4],16);
//				command[23] = (byte) Integer.parseInt(addrs[5],16);
//				command[24] = (byte) Integer.parseInt(addrs[6],16);
//				
				command[24] = (byte) Integer.parseInt(addrs[0],16);
				command[23] = (byte) Integer.parseInt(addrs[1],16);
				command[22] = (byte) Integer.parseInt(addrs[2],16);
				command[21] = (byte) Integer.parseInt(addrs[3],16);
				command[20] = (byte) Integer.parseInt(addrs[4],16);
				command[19] = (byte) Integer.parseInt(addrs[5],16);
				command[18] = (byte) Integer.parseInt(addrs[6],16);
			}
			
			command[25] = 0x00;
			for(int i = 4;i < 25;i++){
				command[25] += command[i];
			}
			command[26] = 0x16;
			
			try {
				
//				MainWindow.serialPort.enableReceiveTimeout(2000);
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 27);
				
				byte[] in = new byte[10];
				countdata = 0;
				countFE = 0;
				isData = 0;
				dataLen = 0;
				dataFinish = 0;
				while(MainWindow.in.read(in) > 0){
					
					readBytes(in,re);
					if(dataFinish == 1){
						break;
					}
				}
				//deal the data re[]
				
				if(checkSum(re)){
					
					if(re[11] == (byte)0x18 && re[12] == (byte)0xA0){
						String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
						for(int i = 8;i > 1;i--){
							String pre0 = Integer.toHexString(re[i]&0xFF).toUpperCase();
							if(pre0.length() == 1){
								pre0 = "0"+pre0;
							}
//							System.out.println(pre0);
							addr = addr + pre0 +" ";
						}
						showAddrTextField.setText(addr);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// sun

			// byte addr = ;
			int addr = 0;
			try {
				addr = Integer.parseInt(addrTextField.getText());
				if (addr >= 0 && addr <= 255) {

				} else {
					JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=255的数字");
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=255的数字");
				e.printStackTrace();
				return;
			}

			byte[] re = new byte[10];
			byte[] command = new byte[10];
			command[0] = 0x0E;
			command[1] = 0x0D;
			command[2] = 0x0B;
			command[3] = (byte) 0x9A;
			command[4] = (byte) addr;
			command[5] = (byte) 0xFF;
			command[6] = (byte) 0xFF;
			command[7] = (byte) 0xFF;
			command[8] = 0x00;
			for (int i = 0; i < 8; i++) {
				command[8] ^= command[i];
			}

			try {

				MainWindow.out.write(command, 0, 9);
				Thread.sleep(300);
				MainWindow.out.write(command, 0, 9);

				readAddr();
//				command[3] = (byte) 0x03;
//				command[8] = 0x00;
//				for (int i = 0; i < 8; i++) {
//					command[8] ^= command[i];
//				}
//				MainWindow.serialPort.enableReceiveThreshold(9);
//				MainWindow.out.write(command, 0, 9);
//				while (MainWindow.in.read(re) > 0) {
//					re[9] = 0;
//					for (int i = 0; i < 9; i++) {
//						re[9] ^= re[i];
//					}
//					if (re[9] == 0) {
//						showAddrTextField.setText(String.valueOf(re[5] & 0xFF));
//						showNumTextField.setText(Integer.toHexString(
//								re[6] & 0xFF).toUpperCase()
//								+ " "
//								+ Integer.toHexString(re[7] & 0xFF)
//										.toUpperCase());
//						break;
//					}
//				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	public void writeFirst() {
		if (nationalCheckBox.isSelected()) {
			// national
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
			command[13] = (byte) 0x16;
			//length
			command[14] = (byte) 0x08;
			//data
			command[15] = (byte) 0x16;
			command[16] = (byte) 0xA0;
			//serial 
			command[17] = (byte) 0x01;
			
			int first = 0;
			String firstStr = "";
			try {
				firstStr = firstNumTextField.getText();
				if (firstStr.length() != 4) {
					JOptionPane.showMessageDialog(panel_1, "请输入4位数字！");
					return;
				}
				first = Integer.parseInt(firstStr);
				if (first >= 0 && first <= 9999) {

				} else {
					JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
				e.printStackTrace();
				return;
			}
			
			
			command[18] = (byte) 0x00;  //.00   decimal
			
			String qb = firstStr.substring(0, 2);
			String sg = firstStr.substring(2, 4);
			
			command[19] = (byte) Integer.parseInt(sg, 16);  //sg
			command[20] = (byte) Integer.parseInt(qb, 16);  //qb
			command[21] = (byte) 0x00;  //w
			command[22] = (byte) 0x2C;
			
			command[23] = 0x00;
			for(int i = 4;i < 23;i++){
				command[23] += command[i];
			}
			command[24] = 0x16;
			
			try {
				
//				MainWindow.serialPort.enableReceiveTimeout(2000);
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 25);
				
				byte[] in = new byte[10];
				countdata = 0;
				countFE = 0;
				isData = 0;
				dataLen = 0;
				dataFinish = 0;
				while(MainWindow.in.read(in) > 0){
					
					readBytes(in,re);
					if(dataFinish == 1){
						break;
					}
				}
				//deal the data re[]
				
				if(checkSum(re)){
					
					if(re[11] == (byte)0x16 && re[12] == (byte)0xA0){
						if(re[14] == (byte)0x00 && re[15] == (byte)0x00){
							JOptionPane.showMessageDialog(panel_1, "修改成功！");
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// sun

			// byte addr = ;
			int first = 0;
			String firstStr = "";
			try {
				firstStr = firstNumTextField.getText();
				if (firstStr.length() != 4) {
					JOptionPane.showMessageDialog(panel_1, "请输入4位数字！");
					return;
				}
				first = Integer.parseInt(firstStr);
				if (first >= 0 && first <= 9999) {

				} else {
					JOptionPane
							.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(panel_1, "请填入>= 0 ,<=9999的数字");
				e.printStackTrace();
				return;
			}

			byte[] re = new byte[10];
			byte[] command = new byte[10];
			command[0] = 0x0E;
			command[1] = 0x0D;
			command[2] = 0x0B;
			command[3] = 0x05;
			command[4] = (byte) 0xFF;
			command[5] = (byte) 0xFF;

			String qb = firstStr.substring(0, 2);
			String sg = firstStr.substring(2, 4);

			command[6] = (byte) Integer.parseInt(qb, 16);
			command[7] = (byte) Integer.parseInt(sg, 16);
			command[8] = 0x00;
			for (int i = 0; i < 8; i++) {
				command[8] ^= command[i];
			}

			try {

				MainWindow.out.write(command, 0, 9);
				Thread.sleep(300);
				MainWindow.out.write(command, 0, 9);

				command[3] = (byte) 0x06;
				command[8] = 0x00;
				for (int i = 0; i < 8; i++) {
					command[8] ^= command[i];
				}
				
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 9);
				byte[] in = new byte[10];
				countdata = 0;
				isE = 0;
				isD = 0;
				isB = 0;
				isData = 0;
				dataFinish = 0;
				
				while(MainWindow.in.read(in) > 0){
					
					readBytesMeter(in, re, 9);
					if(dataFinish == 1){
						break;
					}
					
				}
				
				re[9] = 0;
				for (int i = 0; i < 9; i++) {
					re[9] ^= re[i];
				}
				if (re[9] == 0) {
					// showAddrTextField.setText(String.valueOf(re[5]&0xFF));
					showNumTextField.setText(Integer.toHexString(
							re[6] & 0xFF).toUpperCase()
							+ " "
							+ Integer.toHexString(re[7] & 0xFF)
									.toUpperCase());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	/**
	 * 转到国家标准协议
	 */
	public void writeToNational() {
		if (nationalCheckBox.isSelected()) {
			// national
			JOptionPane.showMessageDialog(panel_1, "协议不支持");
		} else {
			// sun

			byte[] re = new byte[10];
			byte[] command = new byte[10];
			command[0] = 0x0E;
			command[1] = 0x0D;
			command[2] = 0x0B;
			command[3] = 0x07;
			command[4] = (byte) 0xFF;
			command[5] = (byte) 0xFF;

			command[6] = (byte) 0xFF;
			command[7] = (byte) 0xFF;
			command[8] = 0x00;
			for (int i = 0; i < 8; i++) {
				command[8] ^= command[i];
			}

			try {
				
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 9);
				byte[] in = new byte[10];
				countdata = 0;
				isE = 0;
				isD = 0;
				isB = 0;
				isData = 0;
				dataFinish = 0;
				
				while(MainWindow.in.read(in) > 0){
					
					readBytesMeter(in, re, 9);
					if(dataFinish == 1){
						break;
					}
					
				}
				
				re[9] = 0;
				for (int i = 0; i < 9; i++) {
					re[9] ^= re[i];
				}
				if (re[9] == 0 && re[3] == 0x07) {
					// showAddrTextField.setText(String.valueOf(re[5]&0xFF));
					// showNumTextField.setText(Integer.toHexString(re[6]&0xFF).toUpperCase()
					// + " "
					// +Integer.toHexString(re[7]&0xFF).toUpperCase());
					nationalCheckBox.setSelected(true);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	
	/**
	 * 和MCU相同 接收数据
	 * @param in
	 * @param re
	 */
	public void readBytes(byte[] in, byte[] re) {

		if (isData == 0) {
			if (in[0] == (byte) 0xFE) {
				countFE++;
			} else {
				if (in[0] == (byte) 0x68) {
					if (countFE >= 3) {
						isData = 1;
						countFE = 0;
						countdata = 0;
						re[0] = in[0];
						countdata++;
					}
				} else {
					countFE = 0;
				}
			}
		} else {
			re[countdata] = in[0];
			countdata++;
			if (countdata == 11) {
				dataLen = re[10] + 13;
			}
			if (countdata >= 11 && countdata >= dataLen) {
				dataFinish = 1;
				dataLen = 0;
				isData = 0;
				countdata = 0;
			}
		}
	}
	
	/**
	 * 和MCU相同 接收数据
	 * @param in
	 * @param re
	 * @param dataCount cjq 10 meter 9
	 */
	public static void readBytesMeter(byte[] in, byte[] re,int dataCount) {

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
					if(isB == 0){
						if(in[0] == (byte) 0x0B){
							isE = 0;
							isD = 0;
							isB = 0;
							re[0] = 0x0E;
							re[1] = 0x0D;
							re[2] = 0x0B;
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
	
	/**
	 * 检查接收到的数据的校验
	 * @param re
	 * @return
	 */
	public boolean checkSum(byte[] re){
		byte checkSum = 0;
		int len = re[10] + 11;
		for(int i = 0;i < len;i++){
			checkSum += re[i];
		}
		if(checkSum == re[len]){
			return true;
		}
		return false;
	}
	
	public void writeToHD(){
		
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] re = new byte[40];
			byte[] command = new byte[40];
			
			command[0] = (byte) 0xFE;
			command[1] = (byte) 0xFE;
			command[2] = (byte) 0xFE;
			command[3] = (byte) 0xFE;
			
			command[4] = 0x68;
			command[5] = 0x10;
			
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
			command[14] = (byte) 0x03;
			//data
			command[15] = (byte) 0x98;
			command[16] = (byte) 0xA0;
			//serial 
			command[17] = (byte) 0x01;
			
			command[18] = 0x00;
			for(int i = 4;i < 18;i++){
				command[18] += command[i];
			}
			command[19] = 0x16;
			
			try {
				
//				MainWindow.serialPort.enableReceiveTimeout(2000);
				MainWindow.serialPort.enableReceiveThreshold(1);
				MainWindow.out.write(command, 0, 20);
				
				byte[] in = new byte[10];
				countdata = 0;
				countFE = 0;
				isData = 0;
				dataLen = 0;
				dataFinish = 0;
				while(MainWindow.in.read(in) > 0){
					
					readBytes(in,re);
					if(dataFinish == 1){
						break;
					}
				}
				//deal the data re[]
				
				if(checkSum(re)){
					
					if(re[11] == (byte)0x98 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//						int meterread = re[16]&0xFF;
//						meterread = meterread << 8;
//						meterread = meterread | re[15]&0xFF;
//						
//						showNumTextField.setText(Integer.toHexString(meterread));
//						JOptionPane.showMessageDialog(panel_1, "转到海大协议！");
						nationalCheckBox.setSelected(false);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(panel_1, "协议不支持");
		}
	}
	
	public void writeOut(){
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		
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
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x19;
		command[16] = (byte) 0xA0;
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
			countdata = 0;
			countFE = 0;
			isData = 0;
			dataLen = 0;
			dataFinish = 0;
			while(MainWindow.in.read(in) > 0){
				
				readBytes(in,re);
				if(dataFinish == 1){
					break;
				}
			}
			//deal the data re[]
			
			if(checkSum(re)){
				
				if(re[11] == (byte)0x19 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "出厂设置成功！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeIAP(){
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		
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
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x96;
		command[16] = (byte) 0xA0;
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
			countdata = 0;
			countFE = 0;
			isData = 0;
			dataLen = 0;
			dataFinish = 0;
			while(MainWindow.in.read(in) > 0){
				
				readBytes(in,re);
				if(dataFinish == 1){
					break;
				}
			}
			//deal the data re[]
			
			if(checkSum(re)){
				
				if(re[11] == (byte)0x96 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "IAP设置成功，给表断电之后，上电，使用IAP更新程序！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeBad(){
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		
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
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x97;
		command[16] = (byte) 0xA0;
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
			countdata = 0;
			countFE = 0;
			isData = 0;
			dataLen = 0;
			dataFinish = 0;
			while(MainWindow.in.read(in) > 0){
				
				readBytes(in,re);
				if(dataFinish == 1){
					break;
				}
			}
			//deal the data re[]
			
			if(checkSum(re)){
				
				if(re[11] == (byte)0x97 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "I am Bad！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeGood(){
		byte[] re = new byte[40];
		byte[] command = new byte[40];
		
		command[0] = (byte) 0xFE;
		command[1] = (byte) 0xFE;
		command[2] = (byte) 0xFE;
		command[3] = (byte) 0xFE;
		
		command[4] = 0x68;
		command[5] = 0x10;
		
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
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x95;
		command[16] = (byte) 0xA0;
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
			countdata = 0;
			countFE = 0;
			isData = 0;
			dataLen = 0;
			dataFinish = 0;
			while(MainWindow.in.read(in) > 0){
				
				readBytes(in,re);
				if(dataFinish == 1){
					break;
				}
			}
			//deal the data re[]
			
			if(checkSum(re)){
				
				if(re[11] == (byte)0x95 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "I am Good！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
