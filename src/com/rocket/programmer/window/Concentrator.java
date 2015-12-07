package com.rocket.programmer.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rocket.obj.Frame;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.serial.task.ReadJZQAll;
import com.rocket.util.Property;
import com.rocket.util.StringUtil;

public class Concentrator extends JDialog {
	private JTextField txt_cjqaddr;
	private JTextField txt_meteraddr;
	private JTextField txt_jzqaddr;
	final JPanel panel_1 = new JPanel();
	
	public static int datacount = 0;
	public static int header = 0;
	public static int data_len = 0;
	public static int frame_len = 0;
	public static boolean data_done = false;
	private JTextField txt_fileaddr;
	
	public static ReadJZQAll readJZQAll = null;
	private JTextField txt_Port;
	private JTextField txt_IP;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Concentrator dialog = new Concentrator();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Concentrator() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("集中器");
		setBounds(100, 100, 829, 681);
		getContentPane().setLayout(null);
		
		
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 23, 803, 620);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u91C7\u96C6\u5668", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(35, 107, 643, 70);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btn_cjqadd = new JButton("添加");
		btn_cjqadd.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_cjqadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//cjqaddr
				String cjqaddr = txt_cjqaddr.getText();
				
				if(cjqaddr.length() != 12 || !cjqaddr.matches("[0-9fF]*")){
					JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
					return;
				}
				addCJQ(cjqaddr);
				
			}
		});
		btn_cjqadd.setBounds(26, 24, 73, 23);
		panel_2.add(btn_cjqadd);
		
		JButton btn_cjqdelete = new JButton("删除全部");
		btn_cjqdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				deleteAllCJQ();
				
			}
		});
		btn_cjqdelete.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_cjqdelete.setBounds(125, 24, 99, 23);
		panel_2.add(btn_cjqdelete);
		
		JButton btn_cjqquery = new JButton("查看全部");
		btn_cjqquery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				queryCJQs();
			}
		});
		btn_cjqquery.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_cjqquery.setBounds(249, 24, 99, 23);
		panel_2.add(btn_cjqquery);
		
		txt_cjqaddr = new JTextField();
		txt_cjqaddr.setToolTipText("高～～～～～低");
		txt_cjqaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_cjqaddr.setColumns(10);
		txt_cjqaddr.setBounds(438, 14, 168, 41);
		panel_2.add(txt_cjqaddr);
		
		JLabel label = new JLabel("采集器：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(358, 28, 70, 15);
		panel_2.add(label);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(35, 187, 693, 423);
		panel_1.add(panel_3);
		
		JButton btn_meteradd = new JButton("添加");
		btn_meteradd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//meteraddr
				String meteraddr = txt_meteraddr.getText();
				//cjqaddr
				String cjqaddr = txt_cjqaddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				if(cjqaddr.length() != 12 || !cjqaddr.matches("[0-9fF]*")){
					JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
					return;
				}
				addMeter(cjqaddr,meteraddr);
			}
		});
		btn_meteradd.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_meteradd.setBounds(26, 24, 71, 23);
		panel_3.add(btn_meteradd);
		
		JButton btn_meterdelete = new JButton("删除");
		btn_meterdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMeter();
				
			}
		});
		btn_meterdelete.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_meterdelete.setBounds(125, 24, 99, 23);
		panel_3.add(btn_meterdelete);
		
		JButton btn_meterquery = new JButton("查看");
		btn_meterquery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryMeters();
			}
		});
		btn_meterquery.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_meterquery.setBounds(248, 24, 99, 23);
		panel_3.add(btn_meterquery);
		
		JLabel label_1 = new JLabel("表地址：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(371, 28, 56, 15);
		panel_3.add(label_1);
		
		txt_meteraddr = new JTextField();
		txt_meteraddr.setForeground(Color.BLACK);
		txt_meteraddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_meteraddr.setColumns(10);
		txt_meteraddr.setBackground(Color.WHITE);
		txt_meteraddr.setBounds(437, 14, 169, 41);
		panel_3.add(txt_meteraddr);
		
		JButton btn_addMeters = new JButton("Excel批量");
		btn_addMeters.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_addMeters.setBounds(26, 66, 117, 23);
		panel_3.add(btn_addMeters);
		
		txt_fileaddr = new JTextField();
		txt_fileaddr.setEditable(false);
		txt_fileaddr.setToolTipText("高～～～～～低");
		txt_fileaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_fileaddr.setColumns(10);
		txt_fileaddr.setBounds(173, 67, 433, 23);
		panel_3.add(txt_fileaddr);
		
		JButton btn_readsingle = new JButton("抄单个表");
		btn_readsingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//meteraddr
				String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				readsingle(meteraddr);
			}
		});
		btn_readsingle.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_readsingle.setBounds(26, 246, 93, 23);
		panel_3.add(btn_readsingle);
		
		JButton btn_readall = new JButton("抄全部表");
		btn_readall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readall();
			}
		});
		btn_readall.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_readall.setBounds(173, 246, 93, 23);
		panel_3.add(btn_readall);
		
		JButton btn_mbus = new JButton("表MBUS");
		btn_mbus.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_mbus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyslave((byte)0xAA);
			}
		});
		btn_mbus.setBounds(26, 112, 93, 23);
		panel_3.add(btn_mbus);
		
		JButton btn_485 = new JButton("表485");
		btn_485.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_485.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyslave((byte)0xFF);
			}
		});
		btn_485.setBounds(173, 112, 93, 23);
		panel_3.add(btn_485);
		
		JButton btn_queryslave = new JButton("查询表类型");
		btn_queryslave.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryslave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readSlave();
			}
		});
		btn_queryslave.setBounds(320, 112, 125, 23);
		panel_3.add(btn_queryslave);
		
		JButton btn_test = new JButton("测试");
		btn_test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifytest((byte) 0xFF);
			}
		});
		btn_test.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_test.setBounds(26, 159, 93, 23);
		panel_3.add(btn_test);
		
		JButton btn_out = new JButton("出厂");
		btn_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifytest((byte) 0x00);
			}
		});
		btn_out.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_out.setBounds(173, 159, 93, 23);
		panel_3.add(btn_out);
		
		JButton btn_querytest = new JButton("查询出厂");
		btn_querytest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readTest();
			}
		});
		btn_querytest.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_querytest.setBounds(320, 159, 125, 23);
		panel_3.add(btn_querytest);
		
		JButton btnDI0 = new JButton("DI0在前");
		btnDI0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyDISeq((byte)0xFF);
			}
		});
		btnDI0.setFont(new Font("宋体", Font.PLAIN, 14));
		btnDI0.setBounds(26, 201, 93, 23);
		panel_3.add(btnDI0);
		
		JButton btnDI1 = new JButton("DI1在前");
		btnDI1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyDISeq((byte)0xAA);
			}
		});
		btnDI1.setFont(new Font("宋体", Font.PLAIN, 14));
		btnDI1.setBounds(173, 201, 93, 23);
		panel_3.add(btnDI1);
		
		JButton btn_queryDI = new JButton("查询顺序");
		btn_queryDI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readDISeq();
			}
		});
		btn_queryDI.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryDI.setBounds(320, 201, 125, 23);
		panel_3.add(btn_queryDI);
		
		JButton btn_Open = new JButton("开阀");
		btn_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//meteraddr
				String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				openValve(meteraddr);
			}
		});
		btn_Open.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_Open.setBounds(26, 292, 93, 23);
		panel_3.add(btn_Open);
		
		JButton btn_Close = new JButton("关阀");
		btn_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//meteraddr
				String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				closeValve(meteraddr);
			}
		});
		btn_Close.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_Close.setBounds(173, 292, 93, 23);
		panel_3.add(btn_Close);
		
		JLabel lblIp = new JLabel("IP：");
		lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIp.setFont(new Font("宋体", Font.PLAIN, 14));
		lblIp.setBounds(301, 341, 46, 15);
		panel_3.add(lblIp);
		
		txt_Port = new JTextField();
		txt_Port.setToolTipText("");
		txt_Port.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_Port.setColumns(10);
		txt_Port.setBounds(585, 335, 86, 27);
		panel_3.add(txt_Port);
		
		JLabel label_3 = new JLabel("端口：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("宋体", Font.PLAIN, 14));
		label_3.setBounds(514, 341, 61, 15);
		panel_3.add(label_3);
		
		txt_IP = new JTextField();
		txt_IP.setToolTipText("");
		txt_IP.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_IP.setColumns(10);
		txt_IP.setBounds(356, 335, 150, 27);
		panel_3.add(txt_IP);
		
		JButton btn_IP = new JButton("设置IP");
		btn_IP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				modifyIP();
			}
		});
		btn_IP.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_IP.setBounds(26, 335, 93, 23);
		panel_3.add(btn_IP);
		
		JButton btn_queryIP = new JButton("查看IP");
		btn_queryIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				queryIP();
			}
		});
		btn_queryIP.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryIP.setBounds(173, 337, 93, 23);
		panel_3.add(btn_queryIP);
		btn_addMeters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showOpenDialog(panel_1);
				File f = jfc.getSelectedFile();
				if(f != null){
					txt_fileaddr.setText("导入文件："+f.getAbsolutePath());
					//read the excel and add
					addMeters(f.getAbsolutePath());
					txt_fileaddr.setText("Excel添加完成");
				}
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u96C6\u4E2D\u5668", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(35, 27, 643, 70);
		panel_1.add(panel);
		
		JButton btn_jzqmodifyaddr = new JButton("修改地址");
		btn_jzqmodifyaddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				modifyJZQaddr();
			}
		});
		btn_jzqmodifyaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_jzqmodifyaddr.setBounds(26, 24, 107, 23);
		panel.add(btn_jzqmodifyaddr);
		
		JButton btn_jzqreadaddr = new JButton("查看地址");
		btn_jzqreadaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_jzqreadaddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						readJZQaddr();
						return null;
					}
				}.execute();
				
				
			}
		});
		btn_jzqreadaddr.setBounds(165, 24, 99, 23);
		panel.add(btn_jzqreadaddr);
		
		txt_jzqaddr = new JTextField();
		txt_jzqaddr.setToolTipText("高～～～～～低");
		txt_jzqaddr.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_jzqaddr.setColumns(10);
		txt_jzqaddr.setBounds(438, 14, 168, 41);
		panel.add(txt_jzqaddr);
		
		JLabel label_2 = new JLabel("集中器：");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("宋体", Font.PLAIN, 14));
		label_2.setBounds(358, 28, 70, 15);
		panel.add(label_2);
	}
	
	protected void modifyIP() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[6];
		
		String ip = txt_IP.getText();
		String port = txt_Port.getText();
		
		String[] ips = ip.split("[.]");   // ip.split("\\");
		
		System.out.println(ip);
		System.out.println(ips.length);
		System.out.println(ips[0]);
		if(ips.length != 4){
			JOptionPane.showMessageDialog(panel_1, "IP地址错误！");
			return;
		}
		
		for(int i = 0;i < 4;i++){
			try {
				int ips_ = Integer.parseInt(ips[i]);
				if(ips_ <=0 || ips_ >= 255){
					JOptionPane.showMessageDialog(panel_1, "IP地址错误！");
					return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(panel_1, "IP地址错误！");
				return;
			} 
		}
		
		
		int port_ = Integer.parseInt(port);
		if(port_ <= 0 || port_ >=65535){
			JOptionPane.showMessageDialog(panel_1, "端口错误！");
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
				(byte)0x02, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				queryIP();
			}
			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected void queryIP() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x02, gprsaddr, new byte[0]);
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				//get the ip&port 
				txt_IP.setText((deal[18]&0xFF)+"."+(deal[17]&0xFF)+"."+(deal[16]&0xFF)+"."+(deal[15]&0xFF));
				txt_Port.setText(""+Integer.parseInt(String.format("%02x", deal[20]&0xFF)+String.format("%02x", deal[19]&0xFF), 16));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void closeValve(String meteraddr) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[10];
		framedata[0] = 0x10;
		//meteraddr
		byte[] maddr = StringUtil.string2Byte(meteraddr);
		for(int i = 0;i < 7;i++){
			framedata[1+i] = maddr[6-i];
		}
		
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		
		Frame login = new Frame(10, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONTROL, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x02, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			MainWindow.serialPort.enableReceiveTimeout(20000);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				JOptionPane.showMessageDialog(panel_1, "阀关");
			}
			MainWindow.serialPort.enableReceiveTimeout(Property.getIntValue("TIMEOUT"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void openValve(String meteraddr) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[10];
		framedata[0] = 0x10;
		//meteraddr
		byte[] maddr = StringUtil.string2Byte(meteraddr);
		for(int i = 0;i < 7;i++){
			framedata[1+i] = maddr[6-i];
		}
		
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		
		Frame login = new Frame(10, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONTROL, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x03, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
//			MainWindow.serialPort.enableReceiveTimeout(20000);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				JOptionPane.showMessageDialog(panel_1, "阀开");
			}
			MainWindow.serialPort.enableReceiveTimeout(Property.getIntValue("TIMEOUT"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	//seq~0xFF~DI0在前（默认）   seq~0xAA~DI1在前（千宝通）   
	protected void modifyDISeq(byte seq) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=seq;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0E, gprsaddr, framedata);
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				readDISeq();
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void readDISeq() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0E, gprsaddr, new byte[0]);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			if(data_done){
				if(deal[15] == (byte)0xAA){
					JOptionPane.showMessageDialog(panel_1, "DI1在前");
				}
				if(deal[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "DI0在前");
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void modifytest(byte test) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=test;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0D, gprsaddr, framedata);
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				readTest();
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	private void readTest() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0D, gprsaddr, new byte[0]);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			if(data_done){
				if(deal[15] == (byte)0x00){
					JOptionPane.showMessageDialog(panel_1, "出厂");
				}
				if(deal[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "测试");
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void readall() {
		
		if(readJZQAll == null){
			readJZQAll = new ReadJZQAll(txt_fileaddr,MainWindow.out,MainWindow.in,MainWindow.serialPort);
			readJZQAll.execute();
		}else{
			JOptionPane.showMessageDialog(panel_1, "正在抄表...");
		}
		
	}

	protected void readsingle(String meteraddr) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[11];
		framedata[0] = 0x10;
		//meteraddr
		byte[] maddr = StringUtil.string2Byte(meteraddr);
		for(int i = 0;i < 7;i++){
			framedata[1+i] = maddr[6-i];
		}
		
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		framedata[10] = (byte) 0x00;
		
		Frame login = new Frame(11, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_READMETER, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x04, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			MainWindow.serialPort.enableReceiveTimeout(10000);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
				String meterread = String.format("%02x", deal[31]&0xFF)+String.format("%02x", deal[30]&0xFF)+String.format("%02x", deal[29]&0xFF);
				JOptionPane.showMessageDialog(panel_1, meteraddr+"读数："+meterread);
			}
			MainWindow.serialPort.enableReceiveTimeout(Property.getIntValue("TIMEOUT"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void addMeters(String absolutePath) {
		InputStream input = null;
		HSSFWorkbook wb = null;
		Sheet sheet = null;
		try {
			input = new FileInputStream(absolutePath);
			wb = new HSSFWorkbook(input);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		String cjqaddr = getCellString(row,0);
		int count = Integer.parseInt(getCellString(row, 1));
		

		addCJQ(cjqaddr);
		
		for(int i = 1;i < count+1;i++){
			row = sheet.getRow(i);
			String meteraddr = getCellString(row, 0);
			addMeter(cjqaddr,meteraddr);
		}
		
	}
	
	private void addMeter(String cjqaddr, String meteraddr) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[18];
		framedata[0] = 0x10;
		framedata[1] = (byte) 0x00;
		framedata[2] = (byte) 0x00;
		
		//meteraddr
		byte[] maddr = StringUtil.string2Byte(meteraddr);
		for(int i = 0;i < 7;i++){
			framedata[3+i] = maddr[6-i];
		}
		
		//cjqaddr
		byte[] caddr = StringUtil.string2Byte(cjqaddr);
		for(int i = 0;i < 6;i++){
			framedata[10+i] = caddr[5-i];
		}
		
		framedata[16] = (byte) 0x01;
		framedata[17] = (byte) 0x01;
		
		Frame login = new Frame(18, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x06, gprsaddr, framedata);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
					txt_fileaddr.setText(meteraddr+"表添加成功");
					break;
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	public String getCellString(Row row,int s){
		String str = "";
		Cell cell = row.getCell(s);
		if(cell != null){
			switch (cell.getCellType()){
			case Cell.CELL_TYPE_STRING:
				str = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				str = String.valueOf((long)cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				str = cell.getStringCellValue();
				break;
				default:
				str = cell.getStringCellValue();
				break;
			}
		}
		return str;
	}

	public static void readBytes(byte[] in, byte[] deal) {

		deal[datacount++] = in[0];
		//从deal中查找帧  如果没有找到帧  则放弃。
		//首先查找0x68  直到找到0x68为止
		if(header == 0){
			if(datacount > 5){
				if(deal[0] == 0x68 && deal[5] == 0x68){
					if(deal[1] == deal[3] && deal[2] == deal[4]){
						data_len = (deal[1]&0xFF) | ((deal[2]&0xFF)<<8);
						data_len = data_len >> 2;
						header = 1;
					}
				}
				if(header == 0){
					//give up the data
					datacount = 0;
					header = 0;
					frame_len = 0;
					data_len = 0;
					data_done = false;
				}
			}
		}
		if(header == 1){
			if(datacount >= (data_len + 8)){
				frame_len = data_len+8;
				
				byte cs = 0;
				for(int k = 6;k < frame_len-2;k++){
					cs += deal[k];
				}
				if(cs == deal[frame_len-2] && deal[frame_len-1] == 0x16){
					//the frame is good;
					data_done = true;
				}else{
					//这一帧有错误  放弃
					datacount = 0;
					header = 0;
					frame_len = 0;
					data_len = 0;
					data_done = false;
				}
			}else{
				//收到的数据还不够
			}
		}
	}

	private void readJZQaddr() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x03, gprsaddr, new byte[0]);
		
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				txt_jzqaddr.setText("超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				//get the addr 
				String addrstr = "";
				for(int i = 0;i < 5;i++){
					addrstr = addrstr+String.format("%02x", response[11-i]&0xFF);
				}
				
				txt_jzqaddr.setText(addrstr);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void modifyJZQaddr() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[5];
		
		//jzqaddr
		String jzqaddr = txt_jzqaddr.getText();
		if(jzqaddr.length() != 10 || !jzqaddr.matches("[0-9a-f]*")){
			JOptionPane.showMessageDialog(panel_1, "集中器地址错误！");
			return;
		}
		//jzqaddr
		byte[] jaddr = StringUtil.string2Byte(jzqaddr);
		for(int i = 0;i < 5;i++){
			framedata[i] = jaddr[4-i];
		}
		
		
		Frame login = new Frame(5, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x03, gprsaddr, framedata);
		
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				readJZQaddr();
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void addCJQ(String cjqaddr) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[7];
		framedata[0] = 0x55;
		
		byte[] caddr = StringUtil.string2Byte(cjqaddr);
		for(int i = 0;i < 6;i++){
			framedata[1+i] = caddr[5-i];
		}
		
		Frame login = new Frame(7, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x07, gprsaddr, framedata);
		
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			
			if(data_done){
//				JOptionPane.showMessageDialog(panel_1, "采集器添加成功");
				txt_fileaddr.setText(cjqaddr+"采集器添加成功");
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void deleteAllCJQ() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x07, gprsaddr, new byte[]{(byte)0xAA});
		
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				JOptionPane.showMessageDialog(panel_1, "删除完成");
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	

	private void deleteMeter() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[18];
		framedata[0] = 0x10;
		framedata[1] = (byte) 0x00;
		framedata[2] = (byte) 0x00;
		
		//meteraddr
		String meteraddr = txt_meteraddr.getText();
		//cjqaddr
		String cjqaddr = txt_cjqaddr.getText();
		
		if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
			JOptionPane.showMessageDialog(panel_1, "表地址错误！");
			return;
		}
		
		if(cjqaddr.length() != 12 || !cjqaddr.matches("[0-9]*")){
			JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
			return;
		}
		//meteraddr
		byte[] maddr = StringUtil.string2Byte(meteraddr);
		for(int i = 0;i < 7;i++){
			framedata[3+i] = maddr[6-i];
		}
		
		//cjqaddr
		byte[] caddr = StringUtil.string2Byte(cjqaddr);
		for(int i = 0;i < 6;i++){
			framedata[10+i] = caddr[5-i];
		}
		
		framedata[16] = (byte) 0x00;
		framedata[17] = (byte) 0x01;
		
		Frame login = new Frame(18, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x06, gprsaddr, framedata);
		
		
		try {
//					System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				JOptionPane.showMessageDialog(panel_1, "删除成功");
			}
			
//					System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void queryCJQs() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x07, gprsaddr, new byte[0]);
		
		
		try {
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
//			System.out.println(data_len);
			if(data_done){
				String show = "";
				for(int i = 0;i < (data_len-9)/6;i++){
					byte[] cjqaddrbytes = new byte[6];
					for(int k = 0;k < 6;k++){
						cjqaddrbytes[5-k] = deal[15+6*i+k];
					}
					//get the addr 
					String cjqaddrstr = "";
					for(int k = 0;k < 6;k++){
//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
						cjqaddrstr = cjqaddrstr+String.format("%02x", cjqaddrbytes[k]&0xFF)+" ";
					}
					show = show + cjqaddrstr+"\r\n";
				}
				JOptionPane.showMessageDialog(panel_1, show);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void queryMeters() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[10];
		framedata[0] = 0x10;
		framedata[1] = (byte) 0xFF;
		framedata[2] = (byte) 0xFF;
		framedata[3] = (byte) 0xFF;
		framedata[4] = (byte) 0xFF;
		framedata[5] = (byte) 0xFF;
		framedata[6] = (byte) 0xFF;
		framedata[7] = (byte) 0xFF;
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		
		Frame login = new Frame(10, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x06, gprsaddr, framedata);
		
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[256];
			
			
			boolean rcv_over = false;
			String show = "";
			
			
			Sheet sheet = null;
			Row row = null;
			Workbook wb = new HSSFWorkbook();
			sheet = wb.createSheet("cjq_meter_addrs");
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("采集器地址");
			row.createCell(1).setCellValue("表地址");
			int rowcount = 1;
			
			while(!rcv_over){
				datacount = 0;
				header = 0;
				frame_len = 0;
				data_len = 0;
				data_done = false;
				while(MainWindow.in.read(in) > 0){
					readBytes(in, deal);
					if(data_done){
						break;
					}
				}
				
				System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
				if(data_done){
					if((deal[13]&0x60) == (byte)0x60 || (deal[13]&0x60) == (byte)0x20){
						rcv_over = true;
					}
					//deal the frame
					for(int i = 0;i < (data_len-9-1)/17;i++){
						byte[] maddrbytes = new byte[7];
						byte[] caddrbytes = new byte[6];
						for(int k = 0;k < 7;k++){
							maddrbytes[6-k] = deal[16+17*i+2+k];
						}
						for(int k = 0;k < 6;k++){
							caddrbytes[5-k] = deal[16+17*i+9+k];
						}
						
						String maddrstr = "";
						for(int k = 0;k < 7;k++){
	//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
							maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF)+" ";
						}
						
						String cjqaddrstr = "";
						for(int k = 0;k < 6;k++){
	//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
							cjqaddrstr = cjqaddrstr+String.format("%02x", caddrbytes[k]&0xFF)+" ";
						}
						show = show + cjqaddrstr+"~"+maddrstr+"\r\n";
						row = sheet.createRow(rowcount++);
						row.createCell(0).setCellValue(cjqaddrstr);
						row.createCell(1).setCellValue(maddrstr);
					}
				}else{
					rcv_over = true;
				}
			}
			String excelpath = System.getProperty("user.dir")+"\\所有表"+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime())+".xls";
			System.out.println(excelpath);
			FileOutputStream fileOut = new FileOutputStream(excelpath);
			wb.write(fileOut);
		    fileOut.close();
		    
		    txt_fileaddr.setText("导出到"+excelpath);
			
			System.out.println(show);
			JOptionPane.showMessageDialog(panel_1, "导出到"+excelpath);
			//get the addr 
//					String addrstr = "";
//					for(int i = 0;i < 5;i++){
////						this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
//						addrstr = addrstr+String.format("%02x", deal[11-i]&0xFF);
//					}
//					
//					txt_jzq.setText(addrstr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	//0xAA~MBUS     0xFF~485
	private void modifyslave(byte slave){
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=slave;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0C, gprsaddr, framedata);
		
		try {
//			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			if(data_done){
				readSlave();
			}
//			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void readSlave() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0C, gprsaddr, new byte[0]);
		
		
		try {
			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
			MainWindow.serialPort.enableReceiveThreshold(1);
			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
			byte[] in = new byte[2];
			byte[] deal = new byte[100];
			
			datacount = 0;
			header = 0;
			frame_len = 0;
			data_len = 0;
			data_done = false;
			while(MainWindow.in.read(in) > 0){
				readBytes(in, deal);
				if(data_done){
					break;
				}
			}
			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
			if(data_done){
				if(deal[15] == (byte)0xAA){
					JOptionPane.showMessageDialog(panel_1, "MBUS");
				}
				if(deal[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "485");
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
