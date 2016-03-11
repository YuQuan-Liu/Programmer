package com.rocket.programmer.window;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;
import javax.swing.JTextArea;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.rocket.serial.task.LaoHua188_ReadMeters;
import com.rocket.serial.task.ReadMeter;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class LaoHua188 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String excel_path = "";
	HashMap<String, List<String>> cjqs_map = new LinkedHashMap<String, List<String>>();
	private LaoHua188_ReadMeters readMeters = null;
	JTextArea textArea = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LaoHua188 dialog = new LaoHua188();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LaoHua188() {
		setTitle("老化188");
		setBounds(100, 100, 639, 545);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton excel_btn = new JButton("读Excel");
		excel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return ".xls";
					}
					
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
					        return true;
					    }

						String s = f.getName();
				        int i = s.lastIndexOf('.');

				        String extension = null;
				        if (i > 0 &&  i < s.length() - 1) {
				        	extension = s.substring(i+1).toLowerCase();
				        }

					    if (extension != null) {
					        if (extension.equals("xls")) {
					                return true;
					        } else {
					            return false;
					        }
					    }

					    return false;
					}
				});
				jfc.showOpenDialog(contentPanel);
				File f = jfc.getSelectedFile();
				if(f != null){
					excel_path = f.getAbsolutePath();
					if(!excel_path.equals("")){
						textArea.append("文件位置："+f.getAbsolutePath());
						readExcel(excel_path);
					}
				}
			}
		});
		excel_btn.setBounds(37, 34, 130, 37);
		excel_btn.setFont(new Font("宋体", Font.PLAIN, 14));
		contentPanel.add(excel_btn);
		
		final JButton start_btn = new JButton("开始");
		start_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (start_btn.getText().equalsIgnoreCase("开始")) {
					
					if(readMeters == null){
						readMeters = new LaoHua188_ReadMeters(textArea,cjqs_map);
					}else{
						readMeters.cancel(true);
						readMeters = new LaoHua188_ReadMeters(textArea,cjqs_map);
					}
					start_btn.setText("停止");
					readMeters.execute();
				} else {
					start_btn.setText("开始");
					readMeters.cancel(true);
				}
			}
		});
		start_btn.setFont(new Font("宋体", Font.PLAIN, 14));
		start_btn.setBounds(209, 34, 102, 37);
		contentPanel.add(start_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 603, 402);
		contentPanel.add(scrollPane);
		
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);
	}
	
	public void readExcel(String path){
		InputStream input = null;
		FileOutputStream out = null;
		HSSFWorkbook wb = null;
		Sheet sheet = null;
		try {
			input = new FileInputStream(path);
			wb = new HSSFWorkbook(input);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		sheet = wb.getSheetAt(0);
		Row row = null;
		
		int rows = sheet.getLastRowNum();
		List<String> meters = null;
		//最多100个采集器
		boolean scan_over = false;
		for(int c = 0;!scan_over && c < 100;c++){
			for(int i = 0;i <= rows;i++){
				row = sheet.getRow(i);
				if(i == 0){
					String cjqaddr = getCellString(row, c);
					if(cjqaddr!=null && !cjqaddr.equals("")){
						if(cjqaddr.length()!=12 || !cjqaddr.matches("([0-9])+")){
							textArea.append("\r\n采集器地址错误："+cjqaddr);
							textArea.append("\r\n*****************");
							textArea.append("\r\n*****************");
							textArea.append("\r\n*****************");
							cjqs_map.clear();
							return;
						}
						meters = new ArrayList<String>();
						cjqs_map.put(cjqaddr, meters);
						continue;
					}
					scan_over = true;
					break;
				}
				
				String meteraddr = getCellString(row, c);
				if(meteraddr!= null && !meteraddr.equals("")){
					if(!meteraddr.matches("([0-9])+") || meteraddr.length()!=14){
						textArea.append("\r\n表地址错误："+meteraddr);
						textArea.append("\r\n*****************");
						textArea.append("\r\n*****************");
						textArea.append("\r\n*****************");
						cjqs_map.clear();
						return;
					}
					meters.add(meteraddr);
				}else{
					break;
				}
			}
		}
		
		if(cjqs_map.keySet().size()>0){
			textArea.append("\r\n*********导入数据********");
			for(Entry<String, List<String>> entry : cjqs_map.entrySet()){
				textArea.append("\r\n"+entry.getKey());
				textArea.append("\r\n"+entry.getValue());
			}
			textArea.append("\r\n*****************");
		}else{
			textArea.append("\r\n无数据");
			textArea.append("\r\n*****************");
			textArea.append("\r\n*****************");
			textArea.append("\r\n*****************");
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
}
