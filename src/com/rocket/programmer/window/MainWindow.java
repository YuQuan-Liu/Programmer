package com.rocket.programmer.window;

import gnu.io.CommPortIdentifier;
import gnu.io.RXTXCommDriver;
import gnu.io.SerialPort;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import com.rocket.serial.Serial;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.Property;

public class MainWindow {

	private JFrame frmRocketprogrammer;
	public static SerialPort serialPort = null;
	public static InputStream in = null;
	public static OutputStream out = null;
	
	private Thread reader = null;
	private Thread writer = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmRocketprogrammer.setVisible(true);
					window.frmRocketprogrammer.setFont(new Font("宋体", Font.PLAIN, 14));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRocketprogrammer = new JFrame();
		frmRocketprogrammer.setResizable(false);
		frmRocketprogrammer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(serialPort != null){
					reader.interrupt();
					writer.interrupt();
					serialPort.close();
				}
			}
		});
		frmRocketprogrammer.setTitle("Rocket～Programmer");
		frmRocketprogrammer.setBounds(100, 100, 501, 244);
		frmRocketprogrammer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRocketprogrammer.getContentPane().setLayout(null);
		
		final JLabel label = new JLabel("串口：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(90, 49, 54, 15);
		frmRocketprogrammer.getContentPane().add(label);
		
		final JComboBox comboBox = new JComboBox(Serial.getPortsName());
		comboBox.setFont(new Font("宋体", Font.PLAIN, 14));
		comboBox.setBounds(195, 46, 82, 21);
		frmRocketprogrammer.getContentPane().add(comboBox);
		
		final JButton openBtn = new JButton("打开串口");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(serialPort != null){
					if(reader != null){
						reader.interrupt();
					}
					if(writer != null){
						writer.interrupt();
					}
					serialPort.close();
					serialPort = null;
					openBtn.setText("打开串口");
				}else{
					openBtn.setText("关闭串口");
					RXTXCommDriver driver = new RXTXCommDriver();
					driver.initialize();
					try {
						serialPort = (SerialPort) driver.getCommPort(comboBox.getSelectedItem().toString(), CommPortIdentifier.PORT_SERIAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		openBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		openBtn.setBounds(339, 45, 93, 23);
		frmRocketprogrammer.getContentPane().add(openBtn);
		
		JButton button_4 = new JButton("远传设备配置");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serialPort != null){
					try {
						serialPort.setSerialPortParams(Property.getIntValue("BaudRate"), Property.getIntValue("DATABITS"), Property.getIntValue("STOPBITS"), Property.getIntValue("PARITY"));
						in = serialPort.getInputStream();
						out = serialPort.getOutputStream();
						if(reader != null){
							reader.interrupt();
						}
						if(writer != null){
							writer.interrupt();
						}
						reader = new Thread(new SerialReader(in));
						writer = new Thread(new SerialWriter(out));
						reader.start();
						writer.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//如果把下面的两条语句放到  配置Port前  则配置Port的代码得不到执行
					ConcentratorV2 concentratorV2 = new ConcentratorV2();
					concentratorV2.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frmRocketprogrammer, "先打开串口！");
				}
			}
		});
		button_4.setFont(new Font("宋体", Font.PLAIN, 14));
		button_4.setBounds(26, 115, 134, 45);
		frmRocketprogrammer.getContentPane().add(button_4);
	}
}
