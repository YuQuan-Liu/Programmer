package com.rocket.programmer.window;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rocket.obj.Frame;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.Property;
import com.rocket.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Dialog.ModalityType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class ConcentratorV2 extends JFrame {

	private JPanel contentPane;
	private JTextField txt_jzqaddr;
	private JTextField txt_cjqaddr;
	private JTextField txt_ip;
	private JTextField txt_port;
	private JComboBox combo_config;
	private JTextArea txt_meteraddr;
	private JComboBox combo_query;
	private JTextArea txt_out;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConcentratorV2 frame = new ConcentratorV2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConcentratorV2() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("远传设备配置V2");
		setBounds(100, 100, 880, 719);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("宋体", Font.PLAIN, 14), new Color(0, 0, 0)));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("宋体", Font.PLAIN, 14), new Color(0, 0, 0)));
		panel.setBounds(14, 24, 834, 113);
		contentPane.add(panel);
		
		txt_jzqaddr = new JTextField();
		txt_jzqaddr.setToolTipText("高～～～～～低");
		txt_jzqaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_jzqaddr.setColumns(10);
		txt_jzqaddr.setBounds(98, 19, 168, 31);
		panel.add(txt_jzqaddr);
		
		JLabel label = new JLabel("集中器：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(24, 27, 70, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("采集器：");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(293, 27, 70, 15);
		panel.add(label_1);
		
		txt_cjqaddr = new JTextField();
		txt_cjqaddr.setToolTipText("高～～～～～低");
		txt_cjqaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_cjqaddr.setColumns(10);
		txt_cjqaddr.setBounds(367, 19, 168, 31);
		panel.add(txt_cjqaddr);
		
		JLabel label_2 = new JLabel("表：");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("宋体", Font.PLAIN, 14));
		label_2.setBounds(549, 27, 64, 15);
		panel.add(label_2);
		
		txt_ip = new JTextField();
		txt_ip.setToolTipText("高～～～～～低");
		txt_ip.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_ip.setColumns(10);
		txt_ip.setBounds(98, 58, 168, 31);
		panel.add(txt_ip);
		
		JLabel label_3 = new JLabel("IP：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("宋体", Font.PLAIN, 14));
		label_3.setBounds(24, 66, 70, 15);
		panel.add(label_3);
		
		txt_port = new JTextField();
		txt_port.setToolTipText("高～～～～～低");
		txt_port.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_port.setColumns(10);
		txt_port.setBounds(367, 58, 168, 31);
		panel.add(txt_port);
		
		JLabel label_4 = new JLabel("端口：");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setFont(new Font("宋体", Font.PLAIN, 14));
		label_4.setBounds(293, 66, 70, 15);
		panel.add(label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(627, 23, 193, 66);
		panel.add(scrollPane);
		
		txt_meteraddr = new JTextArea();
		txt_meteraddr.setFont(new Font("宋体", Font.PLAIN, 14));
		scrollPane.setViewportView(txt_meteraddr);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, new Font("宋体", Font.PLAIN, 14), new Color(0, 0, 0)));
		panel_1.setBounds(14, 150, 834, 90);
		contentPane.add(panel_1);
		
		combo_config = new JComboBox();
		combo_config.setModel(new DefaultComboBoxModel(new String[] {"请选择设置项：", "设备地址", "设备IP 端口", "底层MBUS表", "底层485表", "底层采集器", "DI0在前", "DI1在前", "协议188", "协议188bad", "波特率1200", "波特率2400", "波特率4800", "波特率9600", "添加采集器", "删除全部采集器", "添加表", "删除表", "抄采集器单个表", "抄采集器单个通道", "抄采集器全部表", "抄集中器单个表", "抄集中器单个通道", "抄集中器全部", "同步采集器数据", "无线", "有线", "移动", "联通", "清空数据", "重启", "集中器LORA TEST", "采集器LORA API"}));
		combo_config.setSelectedIndex(0);
		combo_config.setFont(new Font("宋体", Font.PLAIN, 14));
		combo_config.setBounds(151, 31, 146, 24);
		panel_1.add(combo_config);
		
		JLabel label_5 = new JLabel("设置选项：");
		label_5.setFont(new Font("宋体", Font.PLAIN, 14));
		label_5.setBounds(44, 34, 93, 18);
		panel_1.add(label_5);
		
		JButton btn_config = new JButton("设置");
		btn_config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						device_config();
						return null;
					}
				}.execute();
			}
		});
		btn_config.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_config.setBounds(385, 30, 113, 27);
		panel_1.add(btn_config);
		
		JButton btn_clear = new JButton("清空");
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_cjqaddr.setText("");
				txt_ip.setText("");
				txt_jzqaddr.setText("");
				txt_meteraddr.setText("");
				txt_out.setText("");
				txt_port.setText("");
			}
		});
		btn_clear.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_clear.setBounds(565, 30, 113, 27);
		panel_1.add(btn_clear);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, new Font("宋体", Font.PLAIN, 14), new Color(0, 0, 0)));
		panel_2.setBounds(14, 253, 834, 90);
		contentPane.add(panel_2);
		
		combo_query = new JComboBox();
		combo_query.setModel(new DefaultComboBoxModel(new String[] {"请选择设置项：", "设备地址", "设备IP 端口", "底层设备", "DI0_DI1", "表协议", "波特率", "所有采集器", "单个表信息", "采集器通道表信息", "全部表信息", "全部抄表结果", "是否在抄表", "有线无线", "移动联通", "检查通道同步", "设备程序版本"}));
		combo_query.setSelectedIndex(0);
		combo_query.setFont(new Font("宋体", Font.PLAIN, 14));
		combo_query.setBounds(151, 31, 146, 24);
		panel_2.add(combo_query);
		
		JLabel label_6 = new JLabel("设置选项：");
		label_6.setFont(new Font("宋体", Font.PLAIN, 14));
		label_6.setBounds(44, 34, 93, 18);
		panel_2.add(label_6);
		
		JButton btn_query = new JButton("查询");
		btn_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						device_query();
						return null;
					}
				}.execute();
			}
		});
		btn_query.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_query.setBounds(385, 30, 113, 27);
		panel_2.add(btn_query);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "\u8F93\u51FA", TitledBorder.LEADING, TitledBorder.TOP, new Font("宋体", Font.PLAIN, 14), null));
		panel_3.setBounds(14, 356, 834, 240);
		contentPane.add(panel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 29, 806, 198);
		panel_3.add(scrollPane_1);
		
		txt_out = new JTextArea();
		txt_out.setFont(new Font("宋体", Font.PLAIN, 14));
		scrollPane_1.setViewportView(txt_out);
	}

	protected void device_query() {
		switch (combo_query.getSelectedItem().toString()) {
		case "请选择设置项：":
			JOptionPane.showMessageDialog(contentPane, "请选择设置项!!!");
			break;
		case "设备地址":
			device_query_addr();
			break;
		case "设备IP 端口":
			device_query_ipport();
			break;
		case "底层设备":
			device_query_slave();
			break;
		case "DI0_DI1":
			device_query_di_seq();
			break;
		case "表协议":
			device_query_protocol();
			break;
		case "波特率":
			device_query_baud();
			break;
		case "所有采集器":
			device_query_cjqs();
			break;
		case "单个表信息":
			device_query_meter((byte)0x11);
			break;
		case "采集器通道表信息":
			device_query_meter((byte)0xAA);
			break;
		case "全部表信息":
			device_query_meter((byte)0xFF);
			break;
		case "全部抄表结果":
			device_query_read_result();
			break;
		case "是否在抄表":
			device_query_reading();
			break;
		case "有线无线":
			device_query_mode();
			break;
		case "移动联通":
			device_query_mobile_unicom();
			break;
		case "检查通道同步":
			device_query_syn();
			break;
		case "设备程序版本":
			device_query_version();
			break;
		}
	}
	
	/**
	 * 将发送接收的帧打印到txt_out
	 * @param frame
	 * @param send
	 */
	private void txt_out_append(byte[] frame, int send) {
		
		String now = new SimpleDateFormat("[HH:mm:ss]").format(new Date());
		String send_recv = " 接收: ";
		if(send == 1){
			send_recv = " 发送: ";
		}
		
		String frame_str = now + send_recv + StringUtil.byteArrayToHexStr(frame, frame.length)+"\r\n";
		
		int frame_out = Property.getIntValue("FRAMEOUT");  //是否将发送接收的指令打印到txt_out
		if(frame_out == 1){
			txt_out.append(frame_str);
		}
	}
	
	private void txt_out_append_data(String data) {
		
		txt_out.append(data+"\r\n");
	}

	
	private void device_query_reading() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_READING, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0x01:
					JOptionPane.showMessageDialog(contentPane, "抄表中...");
					break;
				case (byte)0x00:
					JOptionPane.showMessageDialog(contentPane, "未抄表");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_read_result() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		
		Frame login = new Frame(0, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND),
				Frame.AFN_QUERY, (byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), 
				Frame.FN_ALL_READDATA, gprsaddr, null);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			boolean rcv_over = false;
			int timeout = 0;
			int frame_count = 0;
			int frame_all = 0;
			
			while (!rcv_over && timeout < 10) { // 等待抄表结果 2.5min
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

				if (response == null) {
					// 超时
					System.out.println("超时");
					timeout++;
				} else {
					txt_out_append(response,0);
					byte seq = (byte) (response[13] & 0x0F); // 当前帧的seq
					System.out.println("SEQ:" + seq);
					device_config_ack(seq);
					System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
					frame_all = (response[15] & 0xFF) | ((response[16] & 0xFF) << 8);
					frame_count = (response[17] & 0xFF) | ((response[18] & 0xFF) << 8);
					if (frame_all == frame_count) {
						rcv_over = true;
					}
					// deal the frame
					for (int i = 0; i < (response.length - 8 - 9 - 4 - 1) / 14; i++) {
						byte[] maddrbytes = new byte[7];
						for (int k = 0; k < 7; k++) {
							maddrbytes[6 - k] = response[20 + 14 * i + k];
						}
						String maddrstr = "";
						for (int k = 0; k < 7; k++) {
							maddrstr = maddrstr + String.format("%02x", maddrbytes[k] & 0xFF) + " ";
						}
						String meterread = String.format("%02x", response[20 + 14 * i + 11] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 10] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 9] & 0xFF);

						byte st_l = response[20 + 14 * i + 12];
						byte st_h = response[20 + 14 * i + 13];

						String readresult = "";
						if ((st_l & 0x40) == 0x40) { // timeout 0x40
							readresult = "超时";
						} else { // normal
							switch (st_h & 0x20) {
							case 0x20:
								readresult = "气泡";
								break;
							case 0x30:
								readresult = "致命故障";
								break;
							case 0x80:
								readresult = "强光";
								break;
							default:
								readresult = "正常";
								break;
							}
						}
						String valvestatus = "";
						switch (st_l & 0x03) {
						case 0x00:
							valvestatus = "开"; // 开
							break;
						case 0x01:
							valvestatus = "关"; // 关
							break;
						case 0x02:
							valvestatus = "关"; // 关
							break;
						case 0x03:
							valvestatus = "异常"; // 异常
							break;
						default:
							valvestatus = "异常"; // 异常
							break;
						}
						String data_str = maddrstr + ":" + meterread + ":" + String.format("%02x", st_l & 0xFF) + ":"
										+ String.format("%02x", st_h & 0xFF) + ":" + readresult + ":" + valvestatus;
						System.out.println(data_str);
						txt_out_append_data(data_str);
					}
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_addr() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_ADDR, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				//get the addr 
				String addrstr = "";
				for(int i = 0;i < 5;i++){
					addrstr = addrstr+String.format("%02x", response[11-i]&0xFF);
				}
				
				JOptionPane.showMessageDialog(contentPane, addrstr);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_ipport() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_IP_PORT, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				String ip = (response[18]&0xFF)+"."+(response[17]&0xFF)+"."+(response[16]&0xFF)+"."+(response[15]&0xFF);
				int port = Integer.parseInt(String.format("%02x", response[20]&0xFF)+String.format("%02x", response[19]&0xFF), 16);
				
				JOptionPane.showMessageDialog(contentPane, "ip:"+ip+"\r\nport:"+port);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_cjqs() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_CJQ, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				String show = "";
				for(int i = 0;i < (response.length-8-9)/5;i++){
					byte[] cjqaddrbytes = new byte[5];
					for(int k = 0;k < 5;k++){
						cjqaddrbytes[4-k] = response[15+5*i+k];
					}
					//get the addr 
					String cjqaddrstr = "";
					for(int k = 0;k < 5;k++){
						cjqaddrstr = cjqaddrstr+String.format("%02x", cjqaddrbytes[k]&0xFF)+" ";
					}
					show = show + cjqaddrstr+"\r\n";
				}
				JOptionPane.showMessageDialog(contentPane, show);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 读取设备中表的信息
	 * @param mode  0x11 ~ 单个表  0xAA ~ 单个通道  0xFF ~ 全部表
	 */
	private void device_query_meter(byte mode) {
		// meteraddr
		final String meteraddr = txt_meteraddr.getText();
		// cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();

		
		
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = null;
		byte[] caddr = null; // cjqaddr
		byte[] maddr = null; // meteraddr
		
		switch (mode) {
		case (byte) 0xAA:
			//cjq
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			break;
		case 0x11:
			//cjq meteraddr
			if (meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "表地址错误！");
				return;
			}
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			maddr = StringUtil.string2Byte(meteraddr); // meteraddr
			break;
		}
		
		
		
		switch (mode) {
		case (byte) 0xFF:
			framedata = new byte[1];
			framedata[0] = mode;
			break;
		case (byte) 0xAA:
			framedata = new byte[6];
			framedata[0] = mode;

			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}
			break;
		case 0x11:
			framedata = new byte[13];
			framedata[0] = 0x11;

			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}

			for (int i = 0; i < 7; i++) {
				framedata[6 + i] = maddr[6 - i];
			}
			break;
		}
		
		Frame login = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_METER, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			boolean rcv_over = false;
			int timeout = 0;
			int frame_count = 0;
			int frame_all = 0; 
			byte seq = (byte) 0xFF;
			while(!rcv_over && timeout < 4){
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){  //超时
					timeout++;
				}else{
					txt_out_append(response,0);
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					
					if(mode != 0x11){   //查询采集器单个通道表  全部表时  需要对数据进行应答
						byte seq_ = (byte) (response[13] & 0x0F); // 当前帧的seq
						System.out.println("SEQ:" + seq_);
						device_config_ack(seq_);
						
						if(seq == seq_){
							continue;
						}
						seq = seq_;
					}
					
					frame_all = (response[15]&0xFF) | ((response[16]&0xFF)<<8);
					frame_count = (response[17]&0xFF) | ((response[18]&0xFF)<<8);
					if(frame_all == frame_count){
						rcv_over = true;
					}
					//deal the frame
					byte[] caddrbytes = new byte[5];
					for(int k = 0;k < 5;k++){
						caddrbytes[4-k] = response[15+4+k];
					}

					String cjqaddrstr = "";
					for(int k = 0;k < 5;k++){
						cjqaddrstr = cjqaddrstr+String.format("%02x", caddrbytes[k]&0xFF)+" ";
					}
					for(int i = 0;i < (response.length-8-9-9)/7;i++){
						byte[] maddrbytes = new byte[7];
						
						for(int k = 0;k < 7;k++){
							maddrbytes[6-k] = response[15+4+5+7*i+k];
						}
						
						String maddrstr = "";
						for(int k = 0;k < 7;k++){
							maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF)+" ";
						}
						
						String data_str = "cjqaddr:"+cjqaddrstr+";meteraddr:"+maddrstr;
						System.out.println(data_str);
						txt_out_append_data(data_str);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void device_query_syn() {
		// cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();
		// cjq
		if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
			JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
			return;
		}

		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = new byte[5];
		byte[] caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
		
		for (int i = 0; i < 5; i++) {
			framedata[i] = caddr[4 - i];
		}
		
		Frame login = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_SYN, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("ACK超时");
				JOptionPane.showMessageDialog(contentPane, "ACK超时");
			}else{  //接收到ACK
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				response = (byte[]) SerialReader.queue_in.poll(60, TimeUnit.SECONDS);   //等待SYN结果
				if(response == null){ //超时
					System.out.println("SYN超时");
					JOptionPane.showMessageDialog(contentPane, "SYN超时");
				}else{  //SYN结果
					txt_out_append(response,0);
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					int jzq_meter_count = (response[15]&0xFF) | ((response[16]&0xFF)<<8);
					int cjq_return_meter_count = (response[17]&0xFF) | ((response[18]&0xFF)<<8); 
					int no_meter_count = (response[19]&0xFF) | ((response[20]&0xFF)<<8); 
					String msg = "集中器中表数量:  "+jzq_meter_count+"\r\n采集器中表数量:  "+cjq_return_meter_count + "\r\n集中器中没有的表数量:  "+no_meter_count;
					JOptionPane.showMessageDialog(contentPane, msg);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_slave() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_MBUS, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0xAA:
					JOptionPane.showMessageDialog(contentPane, "MBUS");
					break;
				case (byte)0xBB:
					JOptionPane.showMessageDialog(contentPane, "采集器");
					break;
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "485");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_di_seq() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_DI_SEQ, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0xAA:
					JOptionPane.showMessageDialog(contentPane, "DI1在前");
					break;
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "DI0在前");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_protocol() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_PROTOCOL, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0xEE:
					JOptionPane.showMessageDialog(contentPane, "188bad");
					break;
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "188");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_baud() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_BAUD, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0x12:
					JOptionPane.showMessageDialog(contentPane, "1200");
					break;
				case (byte)0x24:
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "2400");
					break;
				case (byte)0x48:
					JOptionPane.showMessageDialog(contentPane, "4800");
					break;
				case (byte)0x96:
					JOptionPane.showMessageDialog(contentPane, "9600");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_mobile_unicom() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_SIMCARD, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0xAA:
					JOptionPane.showMessageDialog(contentPane, "联通");
					break;
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "移动");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void device_query_mode() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_DEVICE_MODE, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				switch(response[15]){
				case (byte)0xAA:
					JOptionPane.showMessageDialog(contentPane, "有线");
					break;
				case (byte)0xFF:
					JOptionPane.showMessageDialog(contentPane, "无线");
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_query_version() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_VERSION, gprsaddr, null);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(contentPane, response[15]);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected void device_config() {
		switch (combo_config.getSelectedItem().toString()) {
		case "请选择设置项：":
			JOptionPane.showMessageDialog(contentPane, "请选择设置项!!!");
			break;
		case "设备地址":
			device_config_addr();
			break;
		case "设备IP 端口":
			device_config_ip_port();
			break;
		case "底层MBUS表":
			device_config_slave((byte)0xAA);
			break;
		case "底层485表":
			device_config_slave((byte)0xFF);
			break;
		case "底层采集器":
			device_config_slave((byte)0xBB);
			break;
		case "DI0在前":
			device_config_di_seq((byte)0xFF);
			break;
		case "DI1在前":
			device_config_di_seq((byte)0xAA);
			break;
		case "协议188":
			device_config_protocol((byte)0xFF);
			break;
		case "协议188bad":
			device_config_protocol((byte)0xEE);
			break;
		case "波特率1200":
			device_config_baud((byte)0x12);
			break;
		case "波特率2400":
			device_config_baud((byte)0x24);
			break;
		case "波特率4800":
			device_config_baud((byte)0x48);
			break;
		case "波特率9600":
			device_config_baud((byte)0x96);
			break;
		case "添加采集器":
			device_config_addcjq();
			break;
		case "删除全部采集器":
			device_config_deletecjqs();
			break;
		case "添加表":
			device_config_meters((byte)0x01);
			break;
		case "删除表":
			device_config_meters((byte)0x00);
			break;
		case "抄采集器单个表":
			device_config_readcjq((byte)0x11);
			break;
		case "抄采集器单个通道":
			device_config_readcjq((byte)0xAA);
			break;
		case "抄采集器全部表":
			device_config_readcjq((byte)0xFF);
			break;
		case "抄集中器单个表":
			device_config_readjzq((byte)0x11);
			break;
		case "抄集中器单个通道":
			device_config_readjzq((byte)0xAA);
			break;
		case "抄集中器全部":
			device_config_readjzq_all();
			break;
		case "无线":
			device_config_devicemode((byte)0xFF);
			break;
		case "有线":
			device_config_devicemode((byte)0xAA);
			break;
		case "移动":
			device_config_Mobile_Unicom((byte)0xFF);
			break;
		case "联通":
			device_config_Mobile_Unicom((byte)0xAA);
			break;
		case "同步采集器数据":
			device_config_syndata();
			break;
		case "清空数据":
			device_config_erase();
			break;
		case "重启":
			device_config_reboot();
			break;
		case "集中器LORA TEST":
			device_config_loratest();
			break;
		case "采集器LORA API":
			device_config_cjqlora_api();
			break;
		}
	}
	
	private void device_config_readjzq_all() {
		
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = new byte[1];
		framedata[0] = (byte) 0xFF;

		Frame login = new Frame(framedata.length, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND),
				Frame.AFN_READMETER, (byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON),
				Frame.FN_CURRENT_METER, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] ackresponse = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (ackresponse == null) {   // 超时
				System.out.println("超时");
				return;
			}
			if (ackresponse[12] == 0x00) { // 如果是集中器对抄表指令的ACK
				txt_out_append(ackresponse,0);
				System.out.println("jzq ack:" + StringUtil.byteArrayToHexStr(ackresponse, ackresponse.length));
			}

			boolean rcv_over = false;
			int timeout = 0;
			int frame_count = 0;
			int frame_all = 0;
			while (!rcv_over && timeout < 100) { // 等待抄表结果 5min
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

				if (response == null) {   // 超时
					System.out.println("超时");
					timeout++;
				} else {
					txt_out_append(response,0);
					//集中器返回的FAKE帧
					if(response[12] == Frame.AFN_FAKE){
						continue;
					}
					byte seq = (byte) (response[13] & 0x0F); // 当前帧的seq
					System.out.println("SEQ:" + seq);
					device_config_ack(seq);
					System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
					
					frame_all = (response[15] & 0xFF) | ((response[16] & 0xFF) << 8);
					frame_count = (response[17] & 0xFF) | ((response[18] & 0xFF) << 8);
					if (frame_all == frame_count) {
						rcv_over = true;
					}
					// deal the frame
					for (int i = 0; i < (response.length - 8 - 9 - 4 - 1) / 14; i++) {
						byte[] maddrbytes = new byte[7];
						for (int k = 0; k < 7; k++) {
							maddrbytes[6 - k] = response[20 + 14 * i + k];
						}
						String maddrstr = "";
						for (int k = 0; k < 7; k++) {
							maddrstr = maddrstr + String.format("%02x", maddrbytes[k] & 0xFF) + " ";
						}
						String meterread = String.format("%02x", response[20 + 14 * i + 11] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 10] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 9] & 0xFF);

						byte st_l = response[20 + 14 * i + 12];
						byte st_h = response[20 + 14 * i + 13];

						String readresult = "";
						if ((st_l & 0x40) == 0x40) { // timeout 0x40
							readresult = "超时";
						} else { // normal
							switch (st_h & 0x20) {
							case 0x20:
								readresult = "气泡";
								break;
							case 0x30:
								readresult = "致命故障";
								break;
							case 0x80:
								readresult = "强光";
								break;
							default:
								readresult = "正常";
								break;
							}
						}
						String valvestatus = "";
						switch (st_l & 0x03) {
						case 0x00:
							valvestatus = "开"; // 开
							break;
						case 0x01:
							valvestatus = "关"; // 关
							break;
						case 0x02:
							valvestatus = "关"; // 关
							break;
						case 0x03:
							valvestatus = "异常"; // 异常
							break;
						default:
							valvestatus = "异常"; // 异常
							break;
						}
						
						String data_str = maddrstr + ":" + meterread + ":" + String.format("%02x", st_l & 0xFF) + ":"
										+ String.format("%02x", st_h & 0xFF) + ":" + readresult + ":" + valvestatus;
						System.out.println(data_str);
						txt_out_append_data(data_str);
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 抄集中器
	 * @param mode  0x11 ~ 单个表  0xAA ~ 单个通道  0xFF ~ 全部表
	 */
	private void device_config_readjzq(byte mode) {
		// meteraddr
		final String meteraddr = txt_meteraddr.getText();
		// cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();

		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = null;

		byte[] caddr = null; // cjqaddr
		byte[] maddr = null; // meteraddr

		switch (mode) {
		case (byte) 0xFF:
			break;
		case (byte) 0xAA:
			// cjq
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			break;
		case 0x11:
			// cjq meteraddr
			if (meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "表地址错误！");
				return;
			}
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			maddr = StringUtil.string2Byte(meteraddr); // meteraddr
			break;
		}
		
		switch(mode){
		case (byte)0xFF:
			framedata = new byte[1];
			framedata[0] = mode;
			break;
		case (byte)0xAA:
			framedata = new byte[6];
			framedata[0] = mode;
			
			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}
			break;
		case 0x11:
			framedata = new byte[13];
			framedata[0] = 0x11;
			
			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}
			
			for (int i = 0; i < 7; i++) {
				framedata[6 + i] = maddr[6 - i];
			}
			break;
		}
		
		Frame login = new Frame(framedata.length, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_READMETER,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_CURRENT_METER, gprsaddr,
				framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] ackresponse = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (ackresponse == null) {
				// 超时
				System.out.println("超时");
				return;
			}
			if(ackresponse[12] == 0x00){  //如果是集中器对抄表指令的ACK
				txt_out_append(ackresponse,0);
				System.out.println("jzq ack:" + StringUtil.byteArrayToHexStr(ackresponse, ackresponse.length));
			}
			
			
			boolean rcv_over = false;
			int timeout = 0;
			int frame_count = 0;
			int frame_all = 0;
			while(!rcv_over && timeout < 100){   //等待抄表结果  5min
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
					timeout++;
				}else{
					txt_out_append(response,0);
					byte seq = (byte)(response[13]&0x0F);  //当前帧的seq
					System.out.println("SEQ:"+seq);
					device_config_ack(seq);
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					frame_all = (response[15] & 0xFF) | ((response[16] & 0xFF) << 8);
					frame_count = (response[17] & 0xFF) | ((response[18] & 0xFF) << 8);
					if (frame_all == frame_count) {
						rcv_over = true;
					}
					//deal the frame
					for(int i = 0;i < (response.length-8-9-4-1)/14;i++){
						byte[] maddrbytes = new byte[7];
						for(int k = 0;k < 7;k++){
							maddrbytes[6-k] = response[20+14*i+k];
						}
						String maddrstr = "";
						for(int k = 0;k < 7;k++){
							maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF)+" ";
						}
						String meterread = String.format("%02x", response[20+14*i+11]&0xFF)+String.format("%02x", response[20+14*i+10]&0xFF)+String.format("%02x", response[20+14*i+9]&0xFF);
						
						byte st_l = response[20+14*i+12];
						byte st_h = response[20+14*i+13];
						
						String readresult = "";
						if((st_l &0x40) ==0x40){    //timeout  0x40
							readresult = "超时";
						}else{  //normal
							switch(st_h & 0x20){
							case 0x20:
								readresult = "气泡";
								break;
							case 0x30:
								readresult = "致命故障";
								break;
							case 0x80:
								readresult = "强光";
								break;
							default:
								readresult = "正常";
								break;
							}
						}
						String valvestatus = "";
						switch (st_l &0x03) {
						case 0x00:
							valvestatus = "开"; //开
							break;
						case 0x01:
							valvestatus = "关"; //关
							break;
						case 0x02:
							valvestatus = "关"; //关
							break;
						case 0x03:
							valvestatus = "异常"; //异常
							break;
						default:
							valvestatus = "异常"; // 异常
							break;
						}
						String data_str = maddrstr + ":" + meterread + ":" + String.format("%02x", st_l & 0xFF) + ":"
										+ String.format("%02x", st_h & 0xFF) + ":" + readresult + ":" + valvestatus;
						System.out.println(data_str);
						txt_out_append_data(data_str);
						
					}
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_readcjq(byte mode) {
		// meteraddr
		final String meteraddr = txt_meteraddr.getText();
		// cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();

		byte[] device_addr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = null;
		
		
		
		byte[] caddr = null; // cjqaddr
		byte[] maddr = null; // meteraddr
		
		switch (mode) {
		case (byte) 0xAA:
		case (byte) 0xFF:
			//cjq
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			break;
		case 0x11:
			//cjq meteraddr
			if (meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "表地址错误！");
				return;
			}
			if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
				JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
				return;
			}
			caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr
			maddr = StringUtil.string2Byte(meteraddr); // meteraddr
			break;
		}
		

		for (int i = 0; i < 5; i++) {
			device_addr[i] = caddr[i];
		}
		
		
		
		switch (mode) {
		case (byte) 0xAA:
			framedata = new byte[6];
			framedata[0] = mode;
			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}
			break;
		case 0x11:
			framedata = new byte[13];
			framedata[0] = 0x11;
			
			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = caddr[4 - i];
			}
			
			for (int i = 0; i < 7; i++) {
				framedata[6 + i] = maddr[6 - i];
			}
			break;
		case (byte)0xFF:
			framedata = new byte[1];
			framedata[0] = (byte) 0xFF;
			break;
		}

		Frame login = new Frame(framedata.length, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND),
				Frame.AFN_READMETER, (byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON),
				Frame.FN_CURRENT_METER, device_addr, framedata);
		System.out.println("write" + StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] ackresponse = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (ackresponse == null) {
				// 超时
				System.out.println("超时");
				return;
			}
			if(ackresponse[12] == 0x00){  //如果是采集器对抄表指令的ACK
				txt_out_append(ackresponse,0);
				System.out.println("cjq ack:" + StringUtil.byteArrayToHexStr(ackresponse, ackresponse.length));
			}
			
			if(mode == (byte)0xFF){
				return;
			}
			
			boolean rcv_over = false;
			int timeout = 0;
			int frame_count = 0;
			int frame_all = 0;
			while (!rcv_over && timeout < 10) { // 等待抄表结果 2.5min
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

				if (response == null) {
					// 超时
					System.out.println("超时");
					timeout++;
				} else {
					txt_out_append(response,0);
					byte seq = (byte) (response[13] & 0x0F); // 当前帧的seq
					System.out.println("SEQ:" + seq);
					device_config_ack(seq);
					System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
					frame_all = (response[15] & 0xFF) | ((response[16] & 0xFF) << 8);
					frame_count = (response[17] & 0xFF) | ((response[18] & 0xFF) << 8);
					if (frame_all == frame_count) {
						rcv_over = true;
					}
					// deal the frame
					for (int i = 0; i < (response.length - 8 - 9 - 4 - 1) / 14; i++) {
						byte[] maddrbytes = new byte[7];
						for (int k = 0; k < 7; k++) {
							maddrbytes[6 - k] = response[20 + 14 * i + k];
						}
						String maddrstr = "";
						for (int k = 0; k < 7; k++) {
							maddrstr = maddrstr + String.format("%02x", maddrbytes[k] & 0xFF) + " ";
						}
						String meterread = String.format("%02x", response[20 + 14 * i + 11] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 10] & 0xFF)
								+ String.format("%02x", response[20 + 14 * i + 9] & 0xFF);

						byte st_l = response[20 + 14 * i + 12];
						byte st_h = response[20 + 14 * i + 13];

						String readresult = "";
						if ((st_l & 0x40) == 0x40) { // timeout 0x40
							readresult = "超时";
						} else { // normal
							switch (st_h & 0x20) {
							case 0x20:
								readresult = "气泡";
								break;
							case 0x30:
								readresult = "致命故障";
								break;
							case 0x80:
								readresult = "强光";
								break;
							default:
								readresult = "正常";
								break;
							}
						}
						String valvestatus = "";
						switch (st_l & 0x03) {
						case 0x00:
							valvestatus = "开"; // 开
							break;
						case 0x01:
							valvestatus = "关"; // 关
							break;
						case 0x02:
							valvestatus = "关"; // 关
							break;
						case 0x03:
							valvestatus = "异常"; // 异常
							break;
						default:
							valvestatus = "异常"; // 异常
							break;
						}
						String data_str = maddrstr + ":" + meterread + ":" + String.format("%02x", st_l & 0xFF) + ":"
										+ String.format("%02x", st_h & 0xFF) + ":" + readresult + ":" + valvestatus;
						System.out.println(data_str);
						txt_out_append_data(data_str);
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void device_config_ack(byte seq){
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		
		Frame login = new Frame(0, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_YES,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON | seq), Frame.FN_ACK, gprsaddr, null);
		System.out.println("write"+StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
		try {
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	private void device_config_Mobile_Unicom(byte mobile_unicom) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=mobile_unicom;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_SIMCARD, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readProtocol();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void device_config_devicemode(byte devicemode) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=devicemode;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_DEVICE_MODE, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readProtocol();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void device_config_cjqlora_api() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		byte[] framedata = new byte[1];
		framedata[0] = 0x01;

		Frame login = new Frame(1, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_LORA_API, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {   // 超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
				while(true){
					response = (byte[]) SerialReader.queue_in.poll(10, TimeUnit.SECONDS);
					
					if(response == null){   //超时
						System.out.println("超时,请尝试重新开始");
						JOptionPane.showMessageDialog(contentPane, "超时,请尝试重新开始");
						break;
					}else{
						txt_out_append(response,0);
						txt_out_append_data("采集器LORA接收信号强度:  "+response[15]);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_loratest() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		byte[] framedata = new byte[1];
		framedata[0] = 0x01;

		Frame login = new Frame(1, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_LORA_SEND, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {   // 超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(contentPane, "LORA TEST");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_reboot() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		byte[] framedata = new byte[1];
		framedata[0] = (byte) 0xFF;

		Frame login = new Frame(1, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_RESET, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {   // 超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(contentPane, "重启");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_erase() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		byte[] framedata = new byte[1];
		framedata[0] = (byte) 0xFF;

		Frame login = new Frame(1, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_ERASE, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {   // 超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(contentPane, "FLASH重新初始化");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_syndata() {
		// cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();
		// cjq
		if (cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")) {
			JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
			return;
		}

		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		byte[] framedata = new byte[5];
		byte[] caddr = StringUtil.string2Byte(cjqaddr); // cjqaddr

		for (int i = 0; i < 5; i++) {
			framedata[i] = caddr[4 - i];
		}
		
		Frame login = new Frame(framedata.length, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_SYN, gprsaddr, framedata);

		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {   // 超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(contentPane, "开始同步  等待几分钟后查询同步结果");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 配置表参数
	 * @param add_delete
	 */
	private void device_config_meters(byte add_delete) {
		//meteraddr
		final String meteraddrs = txt_meteraddr.getText();
		//cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();
		
		String[] meteraddr_split = meteraddrs.split("\n");
		System.out.println(meteraddr_split.length);
		
		int metercount = meteraddr_split.length;
		for(int i = 0;i < metercount;i++){  //依次验证每个表
			if(meteraddr_split[i].length() != 14 || !meteraddr_split[i].matches("[0-9]*")){
				JOptionPane.showMessageDialog(contentPane, "表地址错误！");
				return;
			}
		}
		
		if(cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")){
			JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
			return;
		}
		
		//将表地址分成多组添加
		int times = metercount / 10;  //10个一组添加多少次
		int remain = metercount % 10;
		if(remain > 0){
			times += 1;
		}
		
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		for(int i = 0;i < times;i++){
			int meters = 10;
			if(i == times - 1){
				meters = remain;
			}
			
			byte[] framedata = new byte[meters*7+7]; 
			framedata[0] = add_delete;  //运行标志  添加~1  删除~0
			framedata[1] = 0x10;  //表类型
			// cjqaddr
			byte[] caddr = StringUtil.string2Byte(cjqaddr);
			for (int j = 0; j < 5; j++) {
				framedata[2 + j] = caddr[4 - j];
			}
			
			String meters_this = "";   //本次添加的表的地址
			
			for(int j = 0;j < meters;j++){
				meters_this = meteraddr_split[i*10+j] + ":" + meters_this ;
				byte[] maddr = StringUtil.string2Byte(meteraddr_split[i*10+j]);
				for(int z = 0;z < 7;z++){
					framedata[7+7*j + z] =  maddr[6-z];
				}
			}
			
			Frame login = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
					Frame.FN_METER, gprsaddr, framedata);
			
			System.out.println("write:"+StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			try {
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(login.getFrame());
				txt_out_append(login.getFrame(),1);
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				String result = "";
				if(response == null){     //超时
					System.out.println("超时");
					result = meters_this+"超时";
				}else{
					txt_out_append(response,0);
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					
					String data_str = meters_this+"表添加成功";
					System.out.println(data_str);
					txt_out_append_data(data_str);
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void device_config_deletecjqs() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0] = (byte) 0xAA;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_CJQ, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){    //超时
				System.out.println("超时");
				txt_cjqaddr.setText("超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				txt_cjqaddr.setText("全部采集器删除");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void device_config_addcjq() {
		//cjqaddr
		final String cjqaddr = txt_cjqaddr.getText();
		
		if(cjqaddr.length() != 10 || !cjqaddr.matches("[0-9]*")){
			JOptionPane.showMessageDialog(contentPane, "采集器地址错误！");
			return;
		}
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[6];
		framedata[0] = 0x55;
		
		byte[] caddr = StringUtil.string2Byte(cjqaddr);
		for(int i = 0;i < 5;i++){
			framedata[1+i] = caddr[4-i];
		}
		
		Frame login = new Frame(6, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_CJQ, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){    //超时
				System.out.println("超时");
				txt_cjqaddr.setText("超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				txt_cjqaddr.setText(cjqaddr+"采集器添加成功");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void device_config_baud(byte meter_baud) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=meter_baud;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_BAUD, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readProtocol();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void device_config_protocol(byte protocol) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=protocol;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_PROTOCOL, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readProtocol();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void device_config_di_seq(byte seq) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=seq;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_DI_SEQ, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readDISeq();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void device_config_slave(byte slave) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=slave;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_MBUS, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){  //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				readSlave();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void device_config_ip_port() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[6];
		
		String ip = txt_ip.getText();
		String port = txt_port.getText();
		
		String[] ips = ip.split("[.]");   // ip.split("\\");
		
		System.out.println(ip +""+ips.length);
		if(ips.length != 4){
			JOptionPane.showMessageDialog(contentPane, "IP地址错误！");
			return;
		}
		
		for(int i = 0;i < 4;i++){
			try {
				int ips_ = Integer.parseInt(ips[i]);
				if(ips_ <=0 || ips_ >= 255){
					JOptionPane.showMessageDialog(contentPane, "IP地址错误！");
					return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, "IP地址错误！");
				return;
			} 
		}
		
		int port_ = Integer.parseInt(port);
		if(port_ <= 0 || port_ >=65535){
			JOptionPane.showMessageDialog(contentPane, "端口错误！");
			return;
		}
		
		framedata[0] = (byte) Integer.parseInt(ips[3]);
		framedata[1] = (byte) Integer.parseInt(ips[2]);
		framedata[2] = (byte) Integer.parseInt(ips[1]);
		framedata[3] = (byte) Integer.parseInt(ips[0]);
		
		framedata[4] = (byte) port_;
		framedata[5] = (byte) (port_>>8);
		
		Frame login = new Frame(6, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				Frame.FN_IP_PORT, gprsaddr, framedata);
		
		System.out.println("write:"+StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){ //超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(contentPane, "超时");
			}else{
				txt_out_append(response,0);
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
//				queryIP();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}

	private void device_config_addr() {
		byte[] gprsaddr = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

		byte[] framedata = new byte[5];

		// jzqaddr
		String jzqaddr = txt_jzqaddr.getText();
		if (jzqaddr.length() != 10 || !jzqaddr.matches("[0-9]*")) {
			JOptionPane.showMessageDialog(contentPane, "地址错误！");
			return;
		}
		// jzqaddr
		byte[] jaddr = StringUtil.string2Byte(jzqaddr);
		for (int i = 0; i < 5; i++) {
			framedata[i] = jaddr[4 - i];
		}

		Frame login = new Frame(5, (byte) (Frame.ZERO | Frame.PRM_MASTER | Frame.PRM_M_SECOND), Frame.AFN_CONFIG,
				(byte) (Frame.ZERO | Frame.SEQ_FIN | Frame.SEQ_FIR | Frame.SEQ_CON), Frame.FN_ADDR, gprsaddr, framedata);

		try {

			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			txt_out_append(login.getFrame(),1);
			byte[] response = SerialReader.queue_in.poll(3, TimeUnit.SECONDS);

			if (response == null) {// 超时
				System.out.println("超时");
				txt_jzqaddr.setText("超时");
			} else {
				txt_out_append(response,0);
				System.out.println("response" + StringUtil.byteArrayToHexStr(response, response.length));
//				readJZQaddr();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
