package com.rocket.programmer.window;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
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
import com.rocket.obj.ReadResult;
import com.rocket.serial.SerialReader;
import com.rocket.serial.SerialWriter;
import com.rocket.util.StringUtil;

public class Concentrator extends JDialog {
	private JTextField txt_cjqaddr;
	private JTextField txt_meteraddr;
	private JTextField txt_jzqaddr;
	final JPanel panel_1 = new JPanel();
	private JTextField txt_fileaddr;
	
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
		setBounds(100, 100, 829, 739);
		getContentPane().setLayout(null);
		
		
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 23, 803, 678);
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
				final String cjqaddr = txt_cjqaddr.getText();
				
				if(cjqaddr.length() != 12 || !cjqaddr.matches("[0-9fF]*")){
					JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
					return;
				}
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						addCJQ(cjqaddr);
						return null;
					}
				}.execute();
			}
		});
		btn_cjqadd.setBounds(26, 24, 73, 23);
		panel_2.add(btn_cjqadd);
		
		JButton btn_cjqdelete = new JButton("删除全部");
		btn_cjqdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						deleteAllCJQ();
						return null;
					}
				}.execute();
				
				
			}
		});
		btn_cjqdelete.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_cjqdelete.setBounds(125, 24, 99, 23);
		panel_2.add(btn_cjqdelete);
		
		JButton btn_cjqquery = new JButton("查看全部");
		btn_cjqquery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						queryCJQs();
						return null;
					}
				}.execute();
				
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
		panel_3.setBounds(35, 187, 695, 481);
		panel_1.add(panel_3);
		
		JButton btn_meteradd = new JButton("添加");
		btn_meteradd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//meteraddr
				final String meteraddr = txt_meteraddr.getText();
				//cjqaddr
				final String cjqaddr = txt_cjqaddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				if(cjqaddr.length() != 12 || !cjqaddr.matches("[0-9fF]*")){
					JOptionPane.showMessageDialog(panel_1, "采集器地址错误！");
					return;
				}
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						addMeter(cjqaddr,meteraddr,1);
						return null;
					}
				}.execute();
				
			}
		});
		btn_meteradd.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_meteradd.setBounds(26, 24, 71, 23);
		panel_3.add(btn_meteradd);
		
		JButton btn_meterdelete = new JButton("删除");
		btn_meterdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						deleteMeter();
						return null;
					}
				}.execute();
			}
		});
		btn_meterdelete.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_meterdelete.setBounds(125, 24, 99, 23);
		panel_3.add(btn_meterdelete);
		
		JButton btn_meterquery = new JButton("查看");
		btn_meterquery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						queryMeters();
						return null;
					}
				}.execute();
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
				final String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readsingle(meteraddr);
						return null;
					}
				}.execute();
			}
		});
		btn_readsingle.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_readsingle.setBounds(26, 297, 93, 23);
		panel_3.add(btn_readsingle);
		
		JButton btn_readall = new JButton("抄全部表");
		btn_readall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readJZQall();
						return null;
					}
				}.execute();
			}
		});
		btn_readall.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_readall.setBounds(173, 297, 93, 23);
		panel_3.add(btn_readall);
		
		JButton btn_mbus = new JButton("表MBUS");
		btn_mbus.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_mbus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyslave((byte)0xAA);
						return null;
					}
				}.execute();
			}
		});
		btn_mbus.setBounds(26, 112, 93, 23);
		panel_3.add(btn_mbus);
		
		JButton btn_485 = new JButton("表485");
		btn_485.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_485.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyslave((byte)0xFF);
						return null;
					}
				}.execute();
			}
		});
		btn_485.setBounds(173, 112, 93, 23);
		panel_3.add(btn_485);
		
		JButton btn_queryslave = new JButton("查询底层类型");
		btn_queryslave.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryslave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readSlave();
						return null;
					}
				}.execute();
			}
		});
		btn_queryslave.setBounds(450, 112, 125, 23);
		panel_3.add(btn_queryslave);
		
		JButton btnDI0 = new JButton("DI0在前");
		btnDI0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyDISeq((byte)0xFF);
						return null;
					}
				}.execute();
			}
		});
		btnDI0.setFont(new Font("宋体", Font.PLAIN, 14));
		btnDI0.setBounds(26, 161, 93, 23);
		panel_3.add(btnDI0);
		
		JButton btnDI1 = new JButton("DI1在前");
		btnDI1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyDISeq((byte)0xAA);
						return null;
					}
				}.execute();
			}
		});
		btnDI1.setFont(new Font("宋体", Font.PLAIN, 14));
		btnDI1.setBounds(173, 161, 93, 23);
		panel_3.add(btnDI1);
		
		JButton btn_queryDI = new JButton("查询顺序");
		btn_queryDI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readDISeq();
						return null;
					}
				}.execute();
			}
		});
		btn_queryDI.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryDI.setBounds(320, 161, 125, 23);
		panel_3.add(btn_queryDI);
		
		JButton btn_Open = new JButton("开阀");
		btn_Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//meteraddr
				final String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						openValve(meteraddr);
						return null;
					}
				}.execute();
			}
		});
		btn_Open.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_Open.setBounds(26, 343, 93, 23);
		panel_3.add(btn_Open);
		
		JButton btn_Close = new JButton("关阀");
		btn_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//meteraddr
				final String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						closeValve(meteraddr);
						return null;
					}
				}.execute();
			}
		});
		btn_Close.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_Close.setBounds(173, 343, 93, 23);
		panel_3.add(btn_Close);
		
		JLabel lblIp = new JLabel("IP：");
		lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIp.setFont(new Font("宋体", Font.PLAIN, 14));
		lblIp.setBounds(301, 392, 46, 15);
		panel_3.add(lblIp);
		
		txt_Port = new JTextField();
		txt_Port.setToolTipText("");
		txt_Port.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_Port.setColumns(10);
		txt_Port.setBounds(585, 386, 86, 27);
		panel_3.add(txt_Port);
		
		JLabel label_3 = new JLabel("端口：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("宋体", Font.PLAIN, 14));
		label_3.setBounds(514, 392, 61, 15);
		panel_3.add(label_3);
		
		txt_IP = new JTextField();
		txt_IP.setToolTipText("");
		txt_IP.setFont(new Font("宋体", Font.PLAIN, 14));
		txt_IP.setColumns(10);
		txt_IP.setBounds(356, 386, 150, 27);
		panel_3.add(txt_IP);
		
		JButton btn_IP = new JButton("设置IP");
		btn_IP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyIP();
						return null;
					}
				}.execute();
			}
		});
		btn_IP.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_IP.setBounds(26, 386, 93, 23);
		panel_3.add(btn_IP);
		
		JButton btn_queryIP = new JButton("查看IP");
		btn_queryIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						queryIP();
						return null;
					}
				}.execute();
			}
		});
		btn_queryIP.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryIP.setBounds(173, 388, 93, 23);
		panel_3.add(btn_queryIP);
		
		JButton btn_cjq = new JButton("采集器");
		btn_cjq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyslave((byte)0xBB);
						return null;
					}
				}.execute();
			}
		});
		btn_cjq.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_cjq.setBounds(320, 112, 93, 23);
		panel_3.add(btn_cjq);
		
		JButton btn_clean = new JButton("清洗");
		btn_clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//meteraddr
				final String meteraddr = txt_meteraddr.getText();
				
				if(meteraddr.length() != 14 || !meteraddr.matches("[0-9]*")){
					JOptionPane.showMessageDialog(panel_1, "表地址错误！");
					return;
				}
				
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						cleanValve(meteraddr);
						return null;
					}
				}.execute();
			}
		});
		btn_clean.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_clean.setBounds(320, 343, 93, 23);
		panel_3.add(btn_clean);
		
		JButton btn_erase = new JButton("清空数据");
		btn_erase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						eraseFlash();
						return null;
					}
				}.execute();
			}
		});
		btn_erase.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_erase.setBounds(26, 432, 93, 23);
		panel_3.add(btn_erase);
		
		JButton btn_restart = new JButton("重启");
		btn_restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						restart();
						return null;
					}
				}.execute();
			}
		});
		btn_restart.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_restart.setBounds(173, 432, 93, 23);
		panel_3.add(btn_restart);
		
		JButton btn_ack_action = new JButton("先应答");
		btn_ack_action.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyACK_ACTION((byte)0xAA);
						return null;
					}
				}.execute();
			}
		});
		btn_ack_action.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_ack_action.setBounds(26, 211, 93, 23);
		panel_3.add(btn_ack_action);
		
		JButton btn_action_ack = new JButton("先操作");
		btn_action_ack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyACK_ACTION((byte)0xFF);
						return null;
					}
				}.execute();
			}
		});
		btn_action_ack.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_action_ack.setBounds(173, 211, 93, 23);
		panel_3.add(btn_action_ack);
		
		JButton btn_queryactionack = new JButton("查询操作");
		btn_queryactionack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readACK_ACTION();
						return null;
					}
				}.execute();
			}
		});
		btn_queryactionack.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryactionack.setBounds(320, 211, 125, 23);
		panel_3.add(btn_queryactionack);
		
		JButton btn_protocol_188 = new JButton("188表");
		btn_protocol_188.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyProtocol((byte)0xFF);
						return null;
					}
				}.execute();
			}
		});
		btn_protocol_188.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_protocol_188.setBounds(26, 252, 93, 23);
		panel_3.add(btn_protocol_188);
		
		JButton btn_protocol_eg = new JButton("自主表");
		btn_protocol_eg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						modifyProtocol((byte)0x01);
						return null;
					}
				}.execute();
			}
		});
		btn_protocol_eg.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_protocol_eg.setBounds(173, 252, 93, 23);
		panel_3.add(btn_protocol_eg);
		
		JButton btn_queryprotocol = new JButton("查询底层表类型");
		btn_queryprotocol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						readProtocol();
						return null;
					}
				}.execute();
			}
		});
		btn_queryprotocol.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_queryprotocol.setBounds(320, 252, 150, 23);
		panel_3.add(btn_queryprotocol);
		btn_addMeters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						JFileChooser jfc = new JFileChooser();
						jfc.showOpenDialog(panel_1);
						File f = jfc.getSelectedFile();
						if(f != null){
							txt_fileaddr.setText("导入文件："+f.getAbsolutePath());
							//read the excel and add
							addMeters(f.getAbsolutePath());
							txt_fileaddr.setText("导入完成");
						}
						return null;
					}
				}.execute();
				
				
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
				
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						modifyJZQaddr();
						return null;
					}
				}.execute();
				
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				queryIP();
			}
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				txt_IP.setText((response[18]&0xFF)+"."+(response[17]&0xFF)+"."+(response[16]&0xFF)+"."+(response[15]&0xFF));
				txt_Port.setText(""+Integer.parseInt(String.format("%02x", response[20]&0xFF)+String.format("%02x", response[19]&0xFF), 16));
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(30, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "30s超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "阀关");
			}
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(30, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "30s超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "阀开");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	protected void cleanValve(String meteraddr) {
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
				(byte)0x04, gprsaddr, framedata);
		
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "3s超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "清洗开始");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	protected void eraseFlash() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=(byte) 0xFF;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x0F, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "清空数据中，集中器将重启");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	protected void restart() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=(byte) 0xFF;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x10, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "集中器将重启");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	//协议类型 0xFF~188(Default)  1~EG 
	protected void modifyProtocol(byte protocol) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=protocol;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x12, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				readProtocol();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void readProtocol() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x12, gprsaddr, new byte[0]);
		
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[15] == (byte)0x01){
					JOptionPane.showMessageDialog(panel_1, "自主");
				}
				if(response[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "188");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	//先应答后操作~0xaa    先操作后应答~0xff
	protected void modifyACK_ACTION(byte seq) {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[1];
		framedata[0]=seq;
		
		Frame login = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x11, gprsaddr, framedata);
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				readACK_ACTION();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void readACK_ACTION() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x11, gprsaddr, new byte[0]);
		
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[15] == (byte)0xAA){
					JOptionPane.showMessageDialog(panel_1, "先应答");
				}
				if(response[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "先操作");
				}
			}
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
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(login.getFrame());
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
					JOptionPane.showMessageDialog(panel_1, "超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					readDISeq();
				}
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
				SerialWriter.queue_out.clear();
				SerialReader.queue_in.clear();
				SerialWriter.queue_out.put(login.getFrame());
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
					JOptionPane.showMessageDialog(panel_1, "超时");
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if(response[15] == (byte)0xAA){
						JOptionPane.showMessageDialog(panel_1, "DI1在前");
					}
					if(response[15] == (byte)0xFF){
						JOptionPane.showMessageDialog(panel_1, "DI0在前");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	
	protected void readJZQall() {
		byte[] gprsaddr = new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
		
		byte[] framedata = new byte[11];
		framedata[0] = 0x10;
		//meteraddr
		for(int i = 0;i < 7;i++){
			framedata[1+i] = (byte) 0xFF;
		}
		
		framedata[8] = (byte) 0x00;
		framedata[9] = (byte) 0x00;
		framedata[10] = (byte) 0x00;
		
		Frame login = new Frame(11, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_READMETER, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
				(byte)0x04, gprsaddr, framedata);
		
		
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			
			
			boolean rcv_over = false;
			
			Sheet sheet = null;
			Row row = null;
			Workbook wb = new HSSFWorkbook();
			sheet = wb.createSheet("meterread");
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("表地址");
			row.createCell(1).setCellValue("表读数");
			int rowcount = 1;
			
			int timeout = 0;
			while(!rcv_over && timeout < 50){
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					System.out.println("超时");
					timeout++;
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if((response[13]&0x60) == (byte)0x60 || (response[13]&0x60) == (byte)0x20){
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
	//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
							maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF)+" ";
						}
						String meterread = String.format("%02x", response[20+14*i+11]&0xFF)+String.format("%02x", response[20+14*i+10]&0xFF)+String.format("%02x", response[20+14*i+9]&0xFF);
						
						byte st_l = response[20+14*i+12];
						byte st_h = response[20+14*i+13];
						
						String readresult = "";
						if(((st_l &0x40) ==0x40) || ((st_l &0x80)==0x80)){
//							timeout
//							0x40 ~ 表
//							0x80 ~ 采集器
							readresult = "超时";
						}else{
//							normal
							if((st_h & 0x20) == 0x20){
//								remark = "气泡";
								readresult = "气泡";
							}else{
								if((st_h & 0x30) == 0x30){
//									remark = "致命故障";
									readresult = "致命故障";
								}else{
									if((st_h & 0x80) == 0x80){
//										remark = "强光";
										readresult = "强光";
									}else{
//										remark = "";
										readresult = "";
									}
								}
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
							break;
						}
						
						System.out.println(maddrstr + ":"+meterread+":"+String.format("%02x", st_l&0xFF)+":"+String.format("%02x", st_h&0xFF));
//						show = show + cjqaddrstr+"~"+maddrstr+"\r\n";
						row = sheet.createRow(rowcount++);
						row.createCell(0).setCellValue(maddrstr);
						row.createCell(1).setCellValue(meterread);
						row.createCell(2).setCellValue(readresult);
						row.createCell(3).setCellValue(valvestatus);
					}
				}
			}
			
			String excelpath = System.getProperty("user.dir")+"\\表读数"+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime())+".xls";
			FileOutputStream fileOut = new FileOutputStream(excelpath);
			wb.write(fileOut);
		    fileOut.close();
		    
//		    txt_fileaddr.setText("导出到"+excelpath);
		    JOptionPane.showMessageDialog(panel_1, "导出到"+excelpath);
		} catch (Exception e1) {
			e1.printStackTrace();
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(10, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				String meterread = String.format("%02x", response[31]&0xFF)+String.format("%02x", response[30]&0xFF)+String.format("%02x", response[29]&0xFF);
				
				byte st_l = response[32];
				byte st_h = response[33];
				
				String readresult = "";
				if(((st_l &0x40) ==0x40) || ((st_l &0x80)==0x80)){
//					timeout
//					0x40 ~ 表
//					0x80 ~ 采集器
					readresult = "超时";
				}else{
//					normal
					if((st_h & 0x20) == 0x20){
//						remark = "气泡";
						readresult = "气泡";
					}else{
						if((st_h & 0x30) == 0x30){
//							remark = "致命故障";
							readresult = "致命故障";
						}else{
							if((st_h & 0x80) == 0x80){
//								remark = "强光";
								readresult = "强光";
							}else{
//								remark = "";
								readresult = "正常";
							}
						}
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
					break;
				}
				
				JOptionPane.showMessageDialog(panel_1, meteraddr+"  读数："+meterread+"\r\n\r\n  表状态:"+readresult+"  阀门:"+valvestatus);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	protected void addMeters(String absolutePath) {
		InputStream input = null;
		FileOutputStream out = null;
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
		Row row = null;
		
		int rows = sheet.getLastRowNum();
		String cjqaddr_ = "";
		String result = null;
		for(int i = 0;i <= rows;i++){
			row = sheet.getRow(i);
			String cjqaddr = getCellString(row, 0);
			String meteraddr = getCellString(row, 1);
			if(cjqaddr.equals("") || meteraddr.equals("")){
				row.createCell(3).setCellValue("地址为空!");
				break;
			}
			if(!cjqaddr_.equals(cjqaddr)){
				
				addCJQ(cjqaddr);
				cjqaddr_ = cjqaddr;
			}
			result = addMeter(cjqaddr_,meteraddr,0);
			row.createCell(2).setCellValue(result);
		}
		
		try {
			out = new FileOutputStream(absolutePath);
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String addMeter(String cjqaddr, String meteraddr,int show) {
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
		
		String result = "";
		try {
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			
			if(response == null){
				//超时
				System.out.println("超时");
				result = meteraddr+"超时";
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				result = meteraddr+"表添加成功";
			}
			if(show == 1){
				JOptionPane.showMessageDialog(panel_1, result);
			}else{
				txt_fileaddr.setText(result);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
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
				readJZQaddr();
			}
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				txt_fileaddr.setText("超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				txt_fileaddr.setText(cjqaddr+"采集器添加成功");
			}
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
			
			
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(10, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "删除完成");
			}
//			
//			
////			System.out.println(StringUtil.byteArrayToHexStr(login.getFrame(), login.getFrame().length));
//			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
//			byte[] in = new byte[2];
//			byte[] deal = new byte[100];
//			
//			datacount = 0;
//			header = 0;
//			frame_len = 0;
//			data_len = 0;
//			data_done = false;
//			while(MainWindow.in.read(in) > 0){
//				readBytes(in, deal);
//				if(data_done){
//					break;
//				}
//			}
//			if(data_done){
//				JOptionPane.showMessageDialog(panel_1, "删除完成");
//			}
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				JOptionPane.showMessageDialog(panel_1, "删除成功");
			}
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
			
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				String show = "";
				for(int i = 0;i < (response.length-8-9)/6;i++){
					byte[] cjqaddrbytes = new byte[6];
					for(int k = 0;k < 6;k++){
						cjqaddrbytes[5-k] = response[15+6*i+k];
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
			
//			
//			MainWindow.out.write(login.getFrame(), 0, login.getFrame().length);
//			byte[] in = new byte[2];
//			byte[] deal = new byte[100];
//			
//			datacount = 0;
//			header = 0;
//			frame_len = 0;
//			data_len = 0;
//			data_done = false;
//			while(MainWindow.in.read(in) > 0){
//				readBytes(in, deal);
//				if(data_done){
//					break;
//				}
//			}
////			System.out.println(StringUtil.byteArrayToHexStr(deal, datacount));
////			System.out.println(data_len);
//			if(data_done){
//				
//			}
			
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			
			boolean rcv_over = false;
			
			Sheet sheet = null;
			Row row = null;
			Workbook wb = new HSSFWorkbook();
			sheet = wb.createSheet("cjq_meter_addrs");
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("采集器地址");
			row.createCell(1).setCellValue("表地址");
			int rowcount = 1;
			
			int timeout = 0;
			while(!rcv_over && timeout < 4){
				
				
				byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
				
				if(response == null){
					//超时
					timeout++;
				}else{
					System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
					if((response[13]&0x60) == (byte)0x60 || (response[13]&0x60) == (byte)0x20){
						rcv_over = true;
					}
					//deal the frame
					for(int i = 0;i < (response.length-8-9-1)/17;i++){
						byte[] maddrbytes = new byte[7];
						byte[] caddrbytes = new byte[6];
						for(int k = 0;k < 7;k++){
							maddrbytes[6-k] = response[16+17*i+2+k];
						}
						for(int k = 0;k < 6;k++){
							caddrbytes[5-k] = response[16+17*i+9+k];
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
						row = sheet.createRow(rowcount++);
						row.createCell(0).setCellValue(cjqaddrstr);
						row.createCell(1).setCellValue(maddrstr);
					}
				}
			}
			String excelpath = System.getProperty("user.dir")+"\\所有表"+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime())+".xls";
			FileOutputStream fileOut = new FileOutputStream(excelpath);
			wb.write(fileOut);
		    fileOut.close();
		    
//		    txt_fileaddr.setText("导出到"+excelpath);
			
			JOptionPane.showMessageDialog(panel_1, "导出到"+excelpath);
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				readSlave();
			}
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
			SerialWriter.queue_out.clear();
			SerialReader.queue_in.clear();
			SerialWriter.queue_out.put(login.getFrame());
			byte[] response = (byte[]) SerialReader.queue_in.poll(3, TimeUnit.SECONDS);
			
			if(response == null){
				//超时
				System.out.println("超时");
				JOptionPane.showMessageDialog(panel_1, "超时");
			}else{
				System.out.println("response"+StringUtil.byteArrayToHexStr(response, response.length));
				if(response[15] == (byte)0xAA){
					JOptionPane.showMessageDialog(panel_1, "MBUS");
				}
				if(response[15] == (byte)0xBB){
					JOptionPane.showMessageDialog(panel_1, "采集器");
				}
				if(response[15] == (byte)0xFF){
					JOptionPane.showMessageDialog(panel_1, "485");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
