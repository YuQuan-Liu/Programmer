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
import javax.swing.SwingWorker;

import com.rocket.util.Property;
import com.rocket.util.StringPad;
import com.rocket.util.StringUtil;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.serial.task.ReadHalf;
import com.rocket.serial.task.ReadMeter;
import com.rocket.serial.task.TestValve;

import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

public class Meter_NoEncrypt extends JDialog {
	private JTextField showAddrTextField;
	private JTextField showNumTextField;
	private JTextField addrTextField;
	private JTextField firstNumTextField;

	final JCheckBox nationalCheckBox = new JCheckBox("国家推荐协议");
	final JCheckBox di1Checkbox = new JCheckBox("DI1在前");
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
	final JButton toSunBtn = new JButton("转为自主");
	final JButton btn_open = new JButton("开阀");
	final JButton btn_close = new JButton("关阀");
	final JButton btn_testValve = new JButton("循环测试");
	
	private JTextField readNumTextField;
	
	ReadHalf readHalf = null;
	ReadMeter readMeter = null;
	TestValve testValve = null;
	
	static byte[] key = new byte[8];
	private final JButton writeIAPBtn = new JButton("IAP");
	private final JButton clearBtn = new JButton("清空");
	private JButton readMeterBtn;
	private JTextField txtValveTimeout;
	private final JButton btnReadValveTimeout = new JButton("读阀门超时");
	private final JButton btnWriteValveTimeout = new JButton("写阀门超时");
	private JTextField writeAddrResult_txtbox;
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
		setBounds(100, 100, 642, 679);
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
		showNumTextField.setBounds(351, 19, 228, 41);
		panel.add(showNumTextField);

		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 125, 606, 516);
		getContentPane().add(panel_1);

		readAddrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readAddr();
						return null;
					}
				}.execute();
			}
		});
		readAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readAddrBtn.setBounds(46, 32, 93, 23);
		panel_1.add(readAddrBtn);
		readNumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readNum();
						return null;
					}
				}.execute();
			}
		});

		readNumBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readNumBtn.setBounds(46, 91, 93, 23);
		panel_1.add(readNumBtn);
		readHalfBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (readHalfBtn.getText().equalsIgnoreCase("读半位")) {
					
					if(readHalf == null){
						readHalf = new ReadHalf(showNumTextField,nationalCheckBox.isSelected(),di1Checkbox.isSelected());
					}else{
						readHalf.cancel(true);
						readHalf = new ReadHalf(showNumTextField,nationalCheckBox.isSelected(),di1Checkbox.isSelected());
					}
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
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeAddr();
						return null;
					}
				}.execute();
			}
		});

		writeAddrBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeAddrBtn.setBounds(47, 150, 93, 23);
		panel_1.add(writeAddrBtn);

		addrTextField = new JTextField();
		addrTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(addrTextField.getText());
				String raddr = addrTextField.getText();
				writeAddr(raddr);
				addrTextField.setText("");
			}
		});
		addrTextField.setToolTipText("高～～～～～低");
		addrTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		addrTextField.setColumns(10);
		addrTextField.setBounds(185, 151, 218, 21);
		panel_1.add(addrTextField);
		writeFirstNumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeFirst();
						return null;
					}
				}.execute();
			}
		});

		writeFirstNumBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeFirstNumBtn.setBounds(47, 253, 93, 23);
		panel_1.add(writeFirstNumBtn);

		firstNumTextField = new JTextField();
		firstNumTextField.setFont(new Font("宋体", Font.PLAIN, 12));
		firstNumTextField.setColumns(10);
		firstNumTextField.setBounds(185, 254, 218, 21);
		panel_1.add(firstNumTextField);
		writeOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeOut();
						return null;
					}
				}.execute();
			}
		});

		writeOutBtn.setEnabled(false);
		writeOutBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeOutBtn.setBounds(46, 363, 93, 23);
		panel_1.add(writeOutBtn);

		toNationalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeToNational();
						return null;
					}
				}.execute();
			}
		});
		toNationalBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toNationalBtn.setBounds(46, 308, 93, 23);
		panel_1.add(toNationalBtn);
		toSunBtn.setEnabled(false);

		toSunBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeToHD();
						return null;
					}
				}.execute();
				// nationalCheckBox.
			}
		});
		toSunBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		toSunBtn.setBounds(185, 308, 93, 23);
		panel_1.add(toSunBtn);
		
		readNumTextField = new JTextField();
		readNumTextField.setToolTipText("只抄单个表的时候空着，多个表时要填入表的地址");
		readNumTextField.setBounds(185, 92, 218, 21);
		panel_1.add(readNumTextField);
		readNumTextField.setColumns(10);
		writeIAPBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						writeIAP();
						return null;
					}
				}.execute();
			}
		});
		writeIAPBtn.setEnabled(false);
		writeIAPBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		writeIAPBtn.setBounds(185, 363, 93, 23);
		
		panel_1.add(writeIAPBtn);
		
		readMeterBtn = new JButton("读表");
		readMeterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (readMeterBtn.getText().equalsIgnoreCase("读表")) {
					
					if(readMeter == null){
						readMeter = new ReadMeter(showNumTextField,nationalCheckBox.isSelected(),di1Checkbox.isSelected());
					}else{
						readMeter.cancel(true);
						readMeter = new ReadMeter(showNumTextField,nationalCheckBox.isSelected(),di1Checkbox.isSelected());
					}
					readMeterBtn.setText("停止");
					readMeter.execute();
				} else {
					readMeterBtn.setText("读表");
					readMeter.cancel(true);
				}
			}
		});
		readMeterBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		readMeterBtn.setBounds(185, 32, 93, 23);
		panel_1.add(readMeterBtn);
		
		
		btn_open.addActionListener(new ActionListener() {
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
		btn_open.setEnabled(false);
		btn_open.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_open.setBounds(46, 418, 93, 23);
		panel_1.add(btn_open);
		btn_close.addActionListener(new ActionListener() {
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
		
		
		btn_close.setEnabled(false);
		btn_close.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_close.setBounds(193, 418, 93, 23);
		panel_1.add(btn_close);
		btn_testValve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (btn_testValve.getText().equalsIgnoreCase("循环测试")) {
					
					if(testValve == null){
						testValve = new TestValve(di1Checkbox.isSelected());
					}else{
						testValve.cancel(true);
						testValve = new TestValve(di1Checkbox.isSelected());
					}
					btn_testValve.setText("停止");
					testValve.execute();
				} else {
					btn_testValve.setText("循环测试");
					testValve.cancel(true);
				}
			}
		});
		btn_testValve.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_testValve.setEnabled(false);
		btn_testValve.setBounds(324, 418, 93, 23);
		
		panel_1.add(btn_testValve);
		
		btnWriteValveTimeout.setEnabled(false);
		btnWriteValveTimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						int timeout = Integer.parseInt(txtValveTimeout.getText());
						writeValveTimeout(timeout);
						return null;
					}
				}.execute();
			}
		});
		btnWriteValveTimeout.setFont(new Font("宋体", Font.PLAIN, 12));
		btnWriteValveTimeout.setBounds(46, 466, 93, 23);
		panel_1.add(btnWriteValveTimeout);
		
		txtValveTimeout = new JTextField();
		txtValveTimeout.setToolTipText("超时时间多少s");
		txtValveTimeout.setFont(new Font("宋体", Font.PLAIN, 12));
		txtValveTimeout.setColumns(10);
		txtValveTimeout.setBounds(184, 467, 218, 21);
		panel_1.add(txtValveTimeout);
		btnReadValveTimeout.setEnabled(false);
		btnReadValveTimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readValveTimeout();
			}
		});
		btnReadValveTimeout.setFont(new Font("宋体", Font.PLAIN, 12));
		btnReadValveTimeout.setBounds(436, 466, 93, 23);
		
		panel_1.add(btnReadValveTimeout);
		
		writeAddrResult_txtbox = new JTextField();
		writeAddrResult_txtbox.setToolTipText("高～～～～～低");
		writeAddrResult_txtbox.setFont(new Font("宋体", Font.PLAIN, 16));
		writeAddrResult_txtbox.setColumns(10);
		writeAddrResult_txtbox.setBounds(185, 191, 218, 41);
		panel_1.add(writeAddrResult_txtbox);
		
		JLabel label_2 = new JLabel("扫码结果");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(46, 202, 93, 18);
		panel_1.add(label_2);

		nationalCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (nationalCheckBox.isSelected()) {
					writeOutBtn.setEnabled(true);
					writeIAPBtn.setEnabled(true);
					toNationalBtn.setEnabled(false);
					toSunBtn.setEnabled(true);
					
					btn_close.setEnabled(true);
					btn_open.setEnabled(true);
					btn_testValve.setEnabled(true);
					btnWriteValveTimeout.setEnabled(true);
					btnReadValveTimeout.setEnabled(true);
				} else {
					writeOutBtn.setEnabled(false);
					writeIAPBtn.setEnabled(false);
					toNationalBtn.setEnabled(true);
					toSunBtn.setEnabled(false);
					
					btn_close.setEnabled(false);
					btn_open.setEnabled(false);
					btn_testValve.setEnabled(false);
					btnWriteValveTimeout.setEnabled(false);
					btnReadValveTimeout.setEnabled(false);
				}
			}
		});
		nationalCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
		nationalCheckBox.setBounds(6, 96, 103, 23);
		getContentPane().add(nationalCheckBox);
				di1Checkbox.setFont(new Font("宋体", Font.PLAIN, 12));
				
				
				di1Checkbox.setBounds(136, 96, 103, 23);
				getContentPane().add(di1Checkbox);
				clearBtn.setBounds(512, 96, 93, 23);
				getContentPane().add(clearBtn);
		
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
		readHalfBtn.setVisible(false);
		readMeterBtn.setVisible(true);
		

		if (Property.getStringValue("HALF") != null
				&& Property.getStringValue("HALF").equalsIgnoreCase("half")) {
			readHalfBtn.setVisible(true);
//			readMeterBtn.setVisible(true);

		}
	}

	protected void readValveTimeout() {
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
		command[15] = (byte) 0x92;
		command[16] = (byte) 0xA0;
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
				if(response[11] == (byte)0x92 && response[12] == (byte)0xA0){
					byte valvetimeout = response[14];
					
					JOptionPane.showMessageDialog(panel_1, valvetimeout+"s");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void writeValveTimeout(int timeout) {
		byte[] command = new byte[21];
		
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
		command[13] = (byte) 0x04;
		//length
		command[14] = (byte) 0x04;
		//data
		command[15] = (byte) 0x92;
		command[16] = (byte) 0xA0;
		//serial 
		command[17] = (byte) 0x01;
		//open valve
		command[18] = (byte)timeout;
		command[19] = 0x00;
		for(int i = 4;i < 19;i++){
			command[19] += command[i];
		}
		command[20] = (byte)0x16;
		
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
				if(response[11] == (byte)0x92 && response[12] == (byte)0xA0){
					byte valvetimeout = response[14];
					JOptionPane.showMessageDialog(panel_1, valvetimeout+"s");
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
//				command[6] = (byte) 0xAA;
//				command[7] = (byte) 0xAA;
//				command[8] = (byte) 0xAA;
//				command[9] = (byte) 0xAA;
//				command[10] = (byte) 0xAA;
//				command[11] = (byte) 0xAA;
//				command[12] = (byte) 0xAA;
				
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
					command[12] = (byte) Integer.parseInt(addrs[0],16);
					command[11] = (byte) Integer.parseInt(addrs[1],16);
					command[10] = (byte) Integer.parseInt(addrs[2],16);
					command[9] = (byte) Integer.parseInt(addrs[3],16);
					command[8] = (byte) Integer.parseInt(addrs[4],16);
					command[7] = (byte) Integer.parseInt(addrs[5],16);
					command[6] = (byte) Integer.parseInt(addrs[6],16);
				}
				
				//control
				command[13] = (byte) 0x04;
				//length
				command[14] = (byte) 0x04;
				//data
				command[15] = (byte) 0x17;
				command[16] = (byte) 0xA0;
				if(di1Checkbox.isSelected()){
					command[15] = (byte) 0xA0;
					command[16] = (byte) 0x17;
				}
				//serial 
				command[17] = (byte) 0x01;
				//open valve
				command[18] = (byte)0x99;
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
						if(di1Checkbox.isSelected()){
							if(response[11] == (byte)0xA0 && response[12] == (byte)0x17){
								byte st = response[14];
								if((st & 0x03) == 0x02){
									//close 
									JOptionPane.showMessageDialog(panel_1, "关");
								}
							}
						}else{
							if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
								byte st = response[14];
								if((st & 0x03) == 0x02){
									//close 
									JOptionPane.showMessageDialog(panel_1, "关");
								}
							}
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
//		command[6] = (byte) 0xAA;
//		command[7] = (byte) 0xAA;
//		command[8] = (byte) 0xAA;
//		command[9] = (byte) 0xAA;
//		command[10] = (byte) 0xAA;
//		command[11] = (byte) 0xAA;
//		command[12] = (byte) 0xAA;
		
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
			command[12] = (byte) Integer.parseInt(addrs[0],16);
			command[11] = (byte) Integer.parseInt(addrs[1],16);
			command[10] = (byte) Integer.parseInt(addrs[2],16);
			command[9] = (byte) Integer.parseInt(addrs[3],16);
			command[8] = (byte) Integer.parseInt(addrs[4],16);
			command[7] = (byte) Integer.parseInt(addrs[5],16);
			command[6] = (byte) Integer.parseInt(addrs[6],16);
		}
		
		//control
		command[13] = (byte) 0x04;
		//length
		command[14] = (byte) 0x04;
		//data
		command[15] = (byte) 0x17;
		command[16] = (byte) 0xA0;
		if(di1Checkbox.isSelected()){
			command[15] = (byte) 0xA0;
			command[16] = (byte) 0x17;
		}
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
				if(di1Checkbox.isSelected()){
					if(response[12] == (byte)0x17 && response[11] == (byte)0xA0){
						byte st = response[14];
						if((st & 0x03) == 0x00){
							//open 
							JOptionPane.showMessageDialog(panel_1, "开");
						}
					}
				}else{
					if(response[11] == (byte)0x17 && response[12] == (byte)0xA0){
						byte st = response[14];
						if((st & 0x03) == 0x00){
							//open 
							JOptionPane.showMessageDialog(panel_1, "开");
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void readAddr(){
		if(nationalCheckBox.isSelected()){
			//national
			byte[] command = new byte[20];
			
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
			if(di1Checkbox.isSelected()){
				command[15] = (byte) 0x81;
				command[16] = (byte) 0x0A;
			}
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
					if(di1Checkbox.isSelected()){
						if(response[12] == (byte)0x0A && response[11] == (byte)0x81){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
					}else{
						if(response[11] == (byte)0x0A && response[12] == (byte)0x81){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
					}
				}
								
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			//sun
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
					JOptionPane.showMessageDialog(panel_1, "超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					showAddrTextField.setText(String.valueOf(response[5]&0xFF));
					showNumTextField.setText(StringPad.leftPad(Integer.toHexString(response[6]&0xFF).toUpperCase(),2)+ " " +StringPad.leftPad(Integer.toHexString(response[7]&0xFF).toUpperCase(),2));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	
	public void readNum() {
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] command = new byte[20];
			
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
			if(di1Checkbox.isSelected()){
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
					JOptionPane.showMessageDialog(panel_1, "超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if(di1Checkbox.isSelected()){
						if(response[12] == (byte)0x1F && response[11] == (byte)0x90){
							int meterread = response[16]&0xFF;
							meterread = meterread << 8;
							meterread = meterread | response[15]&0xFF;
							
							showNumTextField.setText(Integer.toHexString(meterread));
						}
					}else{
						if(response[11] == (byte)0x1F && response[12] == (byte)0x90){
							int meterread = response[16]&0xFF;
							meterread = meterread << 8;
							meterread = meterread | response[15]&0xFF;
							
							showNumTextField.setText(Integer.toHexString(meterread));
						}
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
					if(addr.equals("")){
						showAddrTextField.setText(String.valueOf(response[5] & 0xFF));
						showNumTextField.setText(StringPad.leftPad(Integer.toHexString(
								response[6] & 0xFF).toUpperCase(),2)
								+ " "
								+ StringPad.leftPad(Integer.toHexString(response[7] & 0xFF)
										.toUpperCase(),2));
					}else{
						showAddrTextField.setText(String.valueOf(response[4] & 0xFF));
						showNumTextField.setText(StringPad.leftPad(Integer.toHexString(
								response[6] & 0xFF).toUpperCase(),2)
								+ " "
								+ StringPad.leftPad(Integer.toHexString(response[7] & 0xFF)
										.toUpperCase(),2));
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void writeAddr(String raddr){
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] command = new byte[27];
			
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
			if(di1Checkbox.isSelected()){
				command[15] = (byte) 0xA0;
				command[16] = (byte) 0x18;
			}
			//serial 
			command[17] = (byte) 0x01;
			
			if(raddr.equals("")){
				JOptionPane.showMessageDialog(panel_1, "输入地址为空！");
				return;
			}else{
				String[] addrs=new String[7];
				if(raddr.length()!=14){
					JOptionPane.showMessageDialog(panel_1, "输入地址长度不对！");
					return;
				}else{
					for(int i = 0;i < 7;i++){
						addrs[i] = raddr.substring(i*2, 2+i*2);
					}
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
				System.out.println(StringUtil.byteArrayToHexStr(command, command.length));
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(command);
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					writeAddrResult_txtbox.setText(raddr+"超时!!!");
					System.out.println("超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if(di1Checkbox.isSelected()){
						if(response[12] == (byte)0x18 && response[11] == (byte)0xA0){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
					}else{
						if(response[11] == (byte)0x18 && response[12] == (byte)0xA0){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
					}
					writeAddrResult_txtbox.setText(raddr+"完成");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void writeAddr() {
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] command = new byte[27];
			
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
			if(di1Checkbox.isSelected()){
				command[15] = (byte) 0xA0;
				command[16] = (byte) 0x18;
			}
			//serial 
			command[17] = (byte) 0x01;
			
			String raddr = addrTextField.getText();
			if(raddr.equals("")){
				JOptionPane.showMessageDialog(panel_1, "输入地址为空！");
				return;
			}else{
				String[] addrs=new String[7];
				if(raddr.length()!=14){
					JOptionPane.showMessageDialog(panel_1, "输入地址长度不对！");
					return;
				}else{
					for(int i = 0;i < 7;i++){
						addrs[i] = raddr.substring(i*2, 2+i*2);
					}
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
				System.out.println(StringUtil.byteArrayToHexStr(command, command.length));
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
					if(di1Checkbox.isSelected()){
						if(response[12] == (byte)0x18 && response[11] == (byte)0xA0){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
					}else{
						if(response[11] == (byte)0x18 && response[12] == (byte)0xA0){
							String addr = "";//Integer.toHexString(re[8]&0xFF).toUpperCase()+" "+Integer.toHexString(re[7]&0xFF)+" "+Integer.toHexString(re[6]&0xFF)+" "+Integer.toHexString(re[5]&0xFF)+" "+Integer.toHexString(re[4]&0xFF)+" "+Integer.toHexString(re[3]&0xFF)+" "+Integer.toHexString(re[2]&0xFF);
							for(int i = 8;i > 1;i--){
								String pre0 = Integer.toHexString(response[i]&0xFF).toUpperCase();
								if(pre0.length() == 1){
									pre0 = "0"+pre0;
								}
								addr = addr + pre0 +" ";
							}
							showAddrTextField.setText(addr);
						}
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
			byte[] command = new byte[9];
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
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(command);
				Thread.sleep(300);
				SerialWriter.queue_out.put(command);

				readAddr();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	public void writeFirst() {
		if (nationalCheckBox.isSelected()) {
			// national
			byte[] command = new byte[25];
			
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
			if(di1Checkbox.isSelected()){
				command[15] = (byte) 0xA0;
				command[16] = (byte) 0x16;
			}
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
					if(di1Checkbox.isSelected()){
						if(response[12] == (byte)0x16 && response[11] == (byte)0xA0){
							if(response[14] == (byte)0x00 && response[15] == (byte)0x00){
								JOptionPane.showMessageDialog(panel_1, "修改成功！");
							}
						}
					}else{
						if(response[11] == (byte)0x16 && response[12] == (byte)0xA0){
							if(response[14] == (byte)0x00 && response[15] == (byte)0x00){
								JOptionPane.showMessageDialog(panel_1, "修改成功！");
							}
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
			
			byte[] command = new byte[9];
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
				
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(command);
				Thread.sleep(300);
				SerialWriter.queue_out.put(command);
				
				command[3] = (byte) 0x06;
				command[8] = 0x00;
				for (int i = 0; i < 8; i++) {
					command[8] ^= command[i];
				}
				SerialWriter.queue_out.put(command);
				
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
					JOptionPane.showMessageDialog(panel_1, "超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
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
	/**
	 * 转到国家标准协议
	 */
	public void writeToNational() {
		if (nationalCheckBox.isSelected()) {
			// national
			JOptionPane.showMessageDialog(panel_1, "协议不支持");
		} else {
			// sun
			byte[] command = new byte[9];
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
					if (response[3] == 0x07) {
						nationalCheckBox.setSelected(true);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	
	
	/**
	 * 检查接收到的数据的校验
	 * @param re
	 * @return
	 */
	public static boolean checkSum(byte[] re){
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
			byte[] command = new byte[20];
			
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
					if(response[11] == (byte)0x98 && response[12] == (byte)0xA0 && response[9] == (byte)0x84){
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
		byte[] command = new byte[20];
		
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
		if(di1Checkbox.isSelected()){
			command[15] = (byte) 0xA0;
			command[16] = (byte) 0x19;
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
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(di1Checkbox.isSelected()){
					if(response[12] == (byte)0x19 && response[11] == (byte)0xA0 && response[9] == (byte)0x84){
//						nationalCheckBox.setSelected(false);
						JOptionPane.showMessageDialog(panel_1, "出厂设置成功！");
					}
				}else{
					if(response[11] == (byte)0x19 && response[12] == (byte)0xA0 && response[9] == (byte)0x84){
//						nationalCheckBox.setSelected(false);
						JOptionPane.showMessageDialog(panel_1, "出厂设置成功！");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeIAP(){
		byte[] command = new byte[20];
		
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
				if(response[11] == (byte)0x96 && response[12] == (byte)0xA0 && response[9] == (byte)0x84){
//					nationalCheckBox.setSelected(false);
					JOptionPane.showMessageDialog(panel_1, "IAP设置成功，给表断电之后，上电，使用IAP更新程序！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
