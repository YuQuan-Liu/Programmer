package com.rocket.programmer.window;

import gnu.io.CommPortIdentifier;
import gnu.io.RXTXCommDriver;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
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
		frmRocketprogrammer.setBounds(100, 100, 500, 254);
		frmRocketprogrammer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRocketprogrammer.getContentPane().setLayout(null);
		
		JButton button = new JButton("表");
		button.setFont(new Font("宋体", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(serialPort != null){
					try {
						serialPort.setSerialPortParams(Property.getIntValue("MeterBaudRate"), Property.getIntValue("DATABITS"), Property.getIntValue("STOPBITS"), Property.getIntValue("PARITY"));
						in = serialPort.getInputStream();
						out = serialPort.getOutputStream();
						reader = new Thread(new SerialReader(in));
						writer = new Thread(new SerialWriter(out));
						reader.start();
						writer.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					//如果把下面的两条语句放到  配置Port前  则配置Port的代码得不到执行
					Meter_NoEncrypt meter = new Meter_NoEncrypt();
					meter.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frmRocketprogrammer, "先打开串口！");
				}
				
			}
		});
		button.setBounds(51, 123, 93, 45);
		frmRocketprogrammer.getContentPane().add(button);
		
		JButton button_1 = new JButton("采集器");
		button_1.setFont(new Font("宋体", Font.PLAIN, 12));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(serialPort != null){
					try {
						serialPort.setSerialPortParams(Property.getIntValue("CollectorBaudRate"), Property.getIntValue("DATABITS"), Property.getIntValue("STOPBITS"), Property.getIntValue("PARITY"));
						in = serialPort.getInputStream();
						out = serialPort.getOutputStream();
						reader = new Thread(new SerialReader(in));
						writer = new Thread(new SerialWriter(out));
						reader.start();
						writer.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Collector collector = new Collector();
					collector.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frmRocketprogrammer, "先打开串口！");
				}
				
			}
		});
		button_1.setBounds(195, 123, 93, 45);
		frmRocketprogrammer.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("集中器");
		button_2.setFont(new Font("宋体", Font.PLAIN, 12));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(serialPort != null){
					try {
						serialPort.setSerialPortParams(Property.getIntValue("ConcentratorBaudRate"), Property.getIntValue("DATABITS"), Property.getIntValue("STOPBITS"), Property.getIntValue("PARITY"));
						in = serialPort.getInputStream();
						out = serialPort.getOutputStream();
						reader = new Thread(new SerialReader(in));
						writer = new Thread(new SerialWriter(out));
						reader.start();
						writer.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Concentrator concentrator = new Concentrator();
					concentrator.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frmRocketprogrammer, "先打开串口！");
				}
			}
		});
		button_2.setBounds(339, 123, 93, 45);
		frmRocketprogrammer.getContentPane().add(button_2);
		
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
	}
}
