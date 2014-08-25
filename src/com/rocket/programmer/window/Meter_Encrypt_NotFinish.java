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

public class Meter_Encrypt_NotFinish extends JDialog {
	private JTextField showAddrTextField;
	private JTextField showNumTextField;
	private JTextField showKeyTextField;
	private JTextField addrTextField;
	private JTextField firstNumTextField;
	private JTextField writeKeyTextField;
	private JTextField keyTextField;

	final JCheckBox nationalCheckBox = new JCheckBox("国家推荐协议");
	final JPanel panel = new JPanel();
	final JLabel label = new JLabel("地址：");
	final JLabel label_1 = new JLabel("读数：");
	final JLabel label_2 = new JLabel("密钥号：");
	final JCheckBox encryptCheckBox = new JCheckBox("加密");
	final JPanel panel_1 = new JPanel();
	final JButton readAddrBtn = new JButton("读表地址");
	final JButton readNumBtn = new JButton("读表数");
	final JButton readKeyBtn = new JButton("读密钥号");
	final JButton readHalfBtn = new JButton("读半位");
	final JButton writeAddrBtn = new JButton("写地址");
	final JButton writeFirstNumBtn = new JButton("写初值");
	final JButton writeKeyBtn = new JButton("写密钥");
	final JButton writeEncryptBtn = new JButton("需要加密");
	final JButton writeOutBtn = new JButton("出厂");
	final JButton toNationalBtn = new JButton("转为国家");
	final JButton toSunBtn = new JButton("转为海大");
	final JButton badGayBtn = new JButton("BAD MAN");
	final JButton goodManBtn = new JButton("GOOD MAN");

	static int countdata = 0;
	static int countFE = 0;
	static int isData = 0;
	static int dataLen = 0;
	static int dataFinish = 0;
	private JTextField readNumTextField;
	
	static byte[] key = new byte[8];
	private final JButton writeIAPBtn = new JButton("IAP");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Meter_Encrypt_NotFinish dialog = new Meter_Encrypt_NotFinish();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Meter_Encrypt_NotFinish() {
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
		showAddrTextField.setText("AA AA AA AA AA AA AA");
		showAddrTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		showAddrTextField.setColumns(10);
		showAddrTextField.setBounds(75, 19, 170, 41);
		panel.add(showAddrTextField);

		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(255, 32, 49, 15);
		panel.add(label_1);

		showNumTextField = new JTextField();
		showNumTextField.setBackground(new Color(255, 255, 255));
		showNumTextField.setForeground(new Color(0, 0, 0));
		showNumTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		showNumTextField.setColumns(10);
		showNumTextField.setBounds(314, 20, 105, 41);
		panel.add(showNumTextField);

		label_2.setFont(new Font("宋体", Font.PLAIN, 14));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(429, 32, 72, 15);
		panel.add(label_2);

		showKeyTextField = new JTextField();
		showKeyTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		showKeyTextField.setColumns(10);
		showKeyTextField.setBounds(511, 19, 74, 41);
		panel.add(showKeyTextField);

		encryptCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (encryptCheckBox.isSelected()) {

					keyTextField.setEnabled(true);
				} else {
					keyTextField.setEnabled(false);
				}
			}
		});
		encryptCheckBox.setEnabled(false);
		encryptCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
		encryptCheckBox.setBounds(134, 96, 61, 23);
		getContentPane().add(encryptCheckBox);

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
		readKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readKey();
			}
		});

		readKeyBtn.setEnabled(false);
		readKeyBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readKeyBtn.setBounds(185, 32, 93, 23);
		panel_1.add(readKeyBtn);

		final ReadHalf readHalf = new ReadHalf(showNumTextField,
				MainWindow.out, MainWindow.in, MainWindow.serialPort);
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
		readHalfBtn.setBounds(324, 32, 93, 23);
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
		writeKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeKey();
			}
		});

		writeKeyBtn.setEnabled(false);
		writeKeyBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeKeyBtn.setBounds(47, 260, 93, 23);
		panel_1.add(writeKeyBtn);

		writeKeyTextField = new JTextField();
		writeKeyTextField.setEnabled(false);
		writeKeyTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		writeKeyTextField.setColumns(10);
		writeKeyTextField.setBounds(185, 261, 218, 21);
		panel_1.add(writeKeyTextField);
		writeEncryptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				needEncrypt();
			}
		});

		writeEncryptBtn.setEnabled(false);
		writeEncryptBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeEncryptBtn.setBounds(324, 315, 93, 23);
		panel_1.add(writeEncryptBtn);
		writeOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeOut();
			}
		});

		writeOutBtn.setEnabled(false);
		writeOutBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeOutBtn.setBounds(46, 370, 93, 23);
		panel_1.add(writeOutBtn);

		toNationalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				writeToNational();
			}
		});
		toNationalBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toNationalBtn.setBounds(46, 315, 93, 23);
		panel_1.add(toNationalBtn);

		toSunBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToHD();
				// nationalCheckBox.
			}
		});
		toSunBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toSunBtn.setBounds(185, 315, 93, 23);
		panel_1.add(toSunBtn);
		badGayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeBad();
			}
		});

		badGayBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		badGayBtn.setBounds(324, 370, 93, 23);
		panel_1.add(badGayBtn);
		goodManBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeGood();
			}
		});

		goodManBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		goodManBtn.setBounds(463, 370, 93, 23);
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
		writeIAPBtn.setBounds(185, 370, 93, 23);
		
		panel_1.add(writeIAPBtn);

		nationalCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (nationalCheckBox.isSelected()) {
					readKeyBtn.setEnabled(true);
					writeKeyTextField.setEnabled(true);
					writeEncryptBtn.setEnabled(true);
					writeOutBtn.setEnabled(true);
					writeKeyBtn.setEnabled(true);
					encryptCheckBox.setEnabled(true);
					encryptCheckBox.setSelected(false);
					writeIAPBtn.setEnabled(true);
				} else {
					readKeyBtn.setEnabled(false);
					writeKeyTextField.setEnabled(false);
					writeEncryptBtn.setEnabled(false);
					writeOutBtn.setEnabled(false);
					writeKeyBtn.setEnabled(false);
					encryptCheckBox.setEnabled(false);
					encryptCheckBox.setSelected(false);
					writeIAPBtn.setEnabled(false);
				}
			}
		});
		nationalCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
		nationalCheckBox.setBounds(6, 96, 103, 23);
		getContentPane().add(nationalCheckBox);

		keyTextField = new JTextField();
		keyTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		keyTextField.setToolTipText("输入完密钥按“回车”");
		keyTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String[] keys = keyTextField.getText().split(" ");
					if(keys.length != 8){
						JOptionPane.showMessageDialog(panel_1, "输入密钥长度不对！");
						return;
					}else{
						for(int i = 0;i < 8;i++){
							try {
//								System.out.println(Integer.parseInt(keys[i],16));
								if(Integer.parseInt(keys[i],16) >= 0 && Integer.parseInt(keys[i],16) <= 255){
									key[i] = (byte)Integer.parseInt(keys[i],16);
								}else{
									JOptionPane.showMessageDialog(panel_1, "输入密钥不对！");
									return;
								}
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(panel_1, "输入密钥不对！");
								e1.printStackTrace();
								return;
							}
						}
						keyTextField.setEnabled(false);
					}
				}
			}
		});
		keyTextField.setEnabled(false);
		keyTextField.setBounds(201, 97, 207, 21);
		getContentPane().add(keyTextField);
		keyTextField.setColumns(10);

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
				command[18] ^= command[i];
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
					if(re[11] == (byte)0x0A && re[12] == (byte)0x81){
						String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
						for(int i = 8;i > 1;i--){
							String pre0 = Integer.toHexString(re[i]&0xFF).toUpperCase();
							if(pre0.length() == 1){
								pre0 = "0"+pre0;
							}
							System.out.println(pre0);
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
				
				MainWindow.serialPort.enableReceiveThreshold(9);
				MainWindow.out.write(command, 0, 9);
				while(MainWindow.in.read(re) > 0){
					re[9] = 0;
					for(int i =0;i < 9;i++){
						re[9] ^= re[i];
					}
					if(re[9] == 0){
						showAddrTextField.setText(String.valueOf(re[5]&0xFF));
						showNumTextField.setText(Integer.toHexString(re[6]&0xFF).toUpperCase() + " " +Integer.toHexString(re[7]&0xFF).toUpperCase());
						break;
					}
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
				command[6] = (byte) Integer.parseInt(addrs[0],16);
				command[7] = (byte) Integer.parseInt(addrs[1],16);
				command[8] = (byte) Integer.parseInt(addrs[2],16);
				command[9] = (byte) Integer.parseInt(addrs[3],16);
				command[10] = (byte) Integer.parseInt(addrs[4],16);
				command[11] = (byte) Integer.parseInt(addrs[5],16);
				command[12] = (byte) Integer.parseInt(addrs[6],16);
			}
			
			//control
			command[13] = (byte) 0x03;
			//length
			command[14] = (byte) 0x03;
			//data
			command[15] = (byte) 0x0A;
			command[16] = (byte) 0x81;
			//serial 
			command[17] = (byte) 0x01;
			
			if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
				encrypt(command);
			}
			
			command[18] = 0x00;
			for(int i = 4;i < 18;i++){
				command[18] ^= command[i];
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
					if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
						encrypt(re);
					}
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

				MainWindow.serialPort.enableReceiveThreshold(9);
				MainWindow.out.write(command, 0, 9);
				while (MainWindow.in.read(re) > 0) {
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
						
						
						break;
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
				command[18] = (byte) Integer.parseInt(addrs[0],16);
				command[19] = (byte) Integer.parseInt(addrs[1],16);
				command[20] = (byte) Integer.parseInt(addrs[2],16);
				command[21] = (byte) Integer.parseInt(addrs[3],16);
				command[22] = (byte) Integer.parseInt(addrs[4],16);
				command[23] = (byte) Integer.parseInt(addrs[5],16);
				command[24] = (byte) Integer.parseInt(addrs[6],16);
			}
			
			command[25] = 0x00;
			for(int i = 4;i < 25;i++){
				command[25] ^= command[i];
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
							System.out.println(pre0);
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
				Thread.sleep(3);
				MainWindow.out.write(command, 0, 9);

				command[3] = (byte) 0x03;
				command[8] = 0x00;
				for (int i = 0; i < 8; i++) {
					command[8] ^= command[i];
				}
				MainWindow.serialPort.enableReceiveThreshold(9);
				MainWindow.out.write(command, 0, 9);
				while (MainWindow.in.read(re) > 0) {
					re[9] = 0;
					for (int i = 0; i < 9; i++) {
						re[9] ^= re[i];
					}
					if (re[9] == 0) {
						showAddrTextField.setText(String.valueOf(re[5] & 0xFF));
						showNumTextField.setText(Integer.toHexString(
								re[6] & 0xFF).toUpperCase()
								+ " "
								+ Integer.toHexString(re[7] & 0xFF)
										.toUpperCase());
						break;
					}
				}
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
			
			
			command[18] = (byte) 0x01;
			command[19] = (byte) 0x01;
			String qb = firstStr.substring(0, 2);
			String sg = firstStr.substring(2, 4);

			command[20] = (byte) Integer.parseInt(qb, 16);
			command[21] = (byte) Integer.parseInt(sg, 16);
			command[22] = (byte) 0x2C;
			
			command[23] = 0x00;
			for(int i = 4;i < 23;i++){
				command[23] ^= command[i];
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
				Thread.sleep(3);
				MainWindow.out.write(command, 0, 9);

				command[3] = (byte) 0x06;
				command[8] = 0x00;
				for (int i = 0; i < 8; i++) {
					command[8] ^= command[i];
				}
				MainWindow.serialPort.enableReceiveThreshold(9);
				MainWindow.out.write(command, 0, 9);
				while (MainWindow.in.read(re) > 0) {
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
						break;
					}
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

				MainWindow.serialPort.enableReceiveThreshold(9);
				MainWindow.out.write(command, 0, 9);
				while (MainWindow.in.read(re) > 0) {
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
						break;
					}
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
	
	/**
	 * 读密钥版本号
	 */
	public void readKey(){
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
		command[13] = (byte) 0x09;
		//length
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x06;
		command[16] = (byte) 0x81;
		//serial 
		command[17] = (byte) 0x01;
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(re[11] == (byte)0x06 && re[12] == (byte)0x81){
//					String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
//					for(int i = 8;i > 1;i--){
//						String pre0 = Integer.toHexString(re[i]&0xFF).toUpperCase();
//						if(pre0.length() == 1){
//							pre0 = "0"+pre0;
//						}
//						System.out.println(pre0);
//						addr = addr + pre0 +" ";
//					}
					String keyVersion = Integer.toHexString(re[14]&0xFF).toUpperCase();
					if(keyVersion.length() == 1){
						keyVersion = "0"+keyVersion;
					}
					showKeyTextField.setText(keyVersion);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void encrypt(byte[] command){
		int times = command[10] / 8;
		int left = command[10] % 8;
		
		for(int i = 0;i < times;i++){
			for(int j = 0;j < 8;j++){
				command[11+i*8+j] = (byte) (command[11+i*8+j]^command[1+j]^key[j]);
			}
		}
		
		for(int i = 0;i < left;i++){
			command[11+times*8+i] = (byte) (command[11+times*8+i]^command[1+i]^key[i]);
		}
	}
	
	public void needEncrypt(){
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
		command[13] = (byte) 0x04;
		//length
		command[14] = (byte) 0x03;
		//data
		command[15] = (byte) 0x99;
		command[16] = (byte) 0xA0;
		//serial 
		command[17] = (byte) 0x01;
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(re[11] == (byte)0x99 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
//					for(int i = 8;i > 1;i--){
//						String pre0 = Integer.toHexString(re[i]&0xFF).toUpperCase();
//						if(pre0.length() == 1){
//							pre0 = "0"+pre0;
//						}
//						System.out.println(pre0);
//						addr = addr + pre0 +" ";
//					}
//					String keyVersion = Integer.toHexString(re[14]&0xFF).toUpperCase();
//					if(keyVersion.length() == 1){
//						keyVersion = "0"+keyVersion;
//					}
//					showKeyTextField.setText("以后需要加密！");
					JOptionPane.showMessageDialog(panel_1, "以后需要加密！,请填入密钥");
					encryptCheckBox.setSelected(true);
					keyTextField.setText("88 88 88 88 88 88 88");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
				encrypt(command);
			}
			
			command[18] = 0x00;
			for(int i = 4;i < 18;i++){
				command[18] ^= command[i];
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
					if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
						encrypt(re);
					}
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
		
		if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
			encrypt(command);
		}
		
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
					encrypt(re);
				}
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
		
		if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
			encrypt(command);
		}
		
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
					encrypt(re);
				}
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
		
		if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
			encrypt(command);
		}
		
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
					encrypt(re);
				}
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
		
		if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
			encrypt(command);
		}
		
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
					encrypt(re);
				}
				if(re[11] == (byte)0x95 && re[12] == (byte)0xA0 && re[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "I am Good！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeKey(){
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
		
		if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
			encrypt(command);
		}
		
		command[18] = 0x00;
		for(int i = 4;i < 18;i++){
			command[18] ^= command[i];
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
				if(encryptCheckBox.isSelected() && !keyTextField.getText().equals("") && !keyTextField.isEnabled()){
					encrypt(re);
				}
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
