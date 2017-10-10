package com.rocket.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Frame {
	
	public static final byte ZERO = 0x00;
	public static final byte DIR_UP = (byte) 0x80;
	public static final byte PRM_MASTER = 0x40;
	public static final byte PRM_M_RESET = 0x00;	//复位   回复确认
	public static final byte PRM_M_LINE = 0x09;		//链路测试  回复确认或否认帧
	public static final byte PRM_M_FIRST = 0x0A;	//请求1级数据  回复确认或否认帧  CON = 1
	public static final byte PRM_M_SECOND = 0x0B;	//请求2级数据  回复的是数据
	
	public static final byte PRM_S_YES = 0x00;		//认可    	确认帧
	public static final byte PRM_S_DATA = 0x08;		//用户数据	响应帧
	public static final byte PRM_S_DENY = 0x09;		//否认  无可召唤的数据	响应帧
	public static final byte PRM_S_LINE = 0x0B;		//链路状态	响应帧
	
	public static final byte SEQ_FIR = 0x40;
	public static final byte SEQ_FIN = 0x20;
	public static final byte SEQ_CON = 0x10;
	
	public static final byte AFN_YES = 0x00;
	public static final byte AFN_LOGIN = 0x02;
	public static final byte AFN_READMETER = 0x0B;
	public static final byte AFN_CONTROL = 0x04;
	public static final byte AFN_CONFIG = 0x03;
	public static final byte AFN_QUERY = 0x0A;
	
	//Fn AFN : AFN_CONFIG  AFN_QUERY
	public static final byte FN_IP_PORT = 2;
	public static final byte FN_ADDR = 3;
	public static final byte FN_METER = 6;
	public static final byte FN_CJQ = 7;
	public static final byte FN_BAUD = 8; 		 //设置抄表波特率
	public static final byte FN_SYN = 9;  		//设置CJQ JZQ表同步
	public static final byte FN_MBUS = 12;  	//底层表的类型   是否打开MBUS部分  发送信息
	public static final byte FN_DI_SEQ = 14;  	//DI0 DI1 顺序   0xAA~DI1在前(千宝通)   0xFF~DI0在前(default)   只在抄表时使用
	public static final byte FN_ERASE = 15;  	//将FLASH清空  重新初始化
	public static final byte FN_RESET = 16;  	//重启系统
	public static final byte FN_ACK_ACTION = 17;	  //先应答后操作~0xaa    先操作后应答~0xff
	public static final byte FN_PROTOCOL = 18;  	//协议类型 0xFF~188(Default)  1~EG  
	public static final byte FN_LORA_SEND = 19;  	//发送供采集器测试信号使用指令 
	public static final byte FN_DEVICE_MODE = 20;	 //设置设备是采集器还是集中器 
	public static final byte FN_READING = 21; 		 //采集器是否在读表
	public static final byte FN_ALL_READDATA = 22; 	 //查询采集器下所有的抄表结果
	public static final byte FN_SIMCARD = 23; 	 //联通移动卡

	public static final byte FN_VERSION = (byte)0xFF;  //程序版本号 
	
	//Fn  AFN :AFN_CONTROL
	public static final byte FN_CLOSE = 2;
	public static final byte FN_OPEN = 3;
	public static final byte FN_CLEAN = 4;   //执行一次开关阀操作  防止生锈  阀门清洗

	//Fn AFN :AFN_READMETER
	public static final byte FN_CURRENT_METER = 4;
	public static final byte FN_READ_CJQ = 5;

	//Fn  AFN AFN_YES 
	public static final byte FN_ACK = 1;
	public static final byte FN_NACK = 2;

	//Fn LINK_TEST   AFN :AFN_LOGIN
	public static final byte FN_HEARTBEAT = 3;
	
	private byte[] frame;
	private int dataLength;
	private int frameLength;
	private byte control;
	private byte dir;
	private byte afn;
	private byte seq;
	private byte fn;
	private byte[] addr;
	private String addrstr = "";
	private byte[] data;
	private byte cs;
	
	public byte[] getFrame() {
		return frame;
	}

	public void setFrame(byte[] frame) {
		this.frame = frame;
	}
	
	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getFrameLength() {
		return frameLength;
	}

	public void setFrameLength(int frameLength) {
		this.frameLength = frameLength;
	}

	public byte getControl() {
		return control;
	}

	public void setControl(byte control) {
		this.control = control;
	}

	public byte getDir() {
		return dir;
	}

	public void setDir(byte dir) {
		this.dir = dir;
	}

	public byte getAfn() {
		return afn;
	}

	public void setAfn(byte afn) {
		this.afn = afn;
	}

	public byte getSeq() {
		return seq;
	}

	public void setSeq(byte seq) {
		this.seq = seq;
	}

	public byte getFn() {
		return fn;
	}

	public void setFn(byte fn) {
		this.fn = fn;
	}

	public byte[] getAddr() {
		return addr;
	}

	public void setAddr(byte[] addr) {
		this.addr = addr;
	}

	public String getAddrstr() {
		return addrstr;
	}

	public void setAddrstr(String addrstr) {
		this.addrstr = addrstr;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte getCs() {
		return cs;
	}

	public void setCs(byte cs) {
		this.cs = cs;
	}

	public Frame() {
	}

	public Frame(byte[] frame) {
		super();
		this.frame = frame;
		this.frameLength = frame.length;
		this.dataLength = this.frameLength-17;
		this.control = frame[6];
		this.dir = (byte) ((this.control & 0xFF) >> 7);
		this.addr = Arrays.copyOfRange(frame, 7, 12);
		for(int i = 0;i < 5;i++){
//			this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
			this.addrstr = this.addrstr+String.format("%02x", addr[i]&0xFF);
		}
		this.addrstr = new StringBuilder(this.addrstr).reverse().toString();
		
		this.data = Arrays.copyOfRange(frame, 12, this.frameLength-2);
		this.afn = frame[12];
		this.seq = frame[13];
		this.fn = frame[14];
		this.cs = frame[this.frameLength-2];
	}

	public Frame(int dataLength, byte control, byte afn, byte seq,
			byte fn, byte[] addr, byte[] data) {
		super();
		this.dataLength = dataLength;
		this.control = control;
		this.dir = (byte) ((control>>7));
		this.afn = afn;
		this.seq = seq;
		this.fn = fn;
		this.data = data;
		//compose the frame
		ByteBuffer bf = ByteBuffer.allocate(dataLength+17);
		bf.order(ByteOrder.LITTLE_ENDIAN);
		bf.put((byte) 0x68);
		bf.putShort((short) (((dataLength+9)<<2)|0x03));
		bf.putShort((short) (((dataLength+9)<<2)|0x03));
		bf.put((byte) 0x68);
		bf.put(control);
		
		for(int i = 0;i < 5;i++){
			bf.put(addr[4-i]);
		}
		bf.put(afn);
		bf.put(seq);
		bf.put(fn);
		if(dataLength > 0){
			bf.put(data);
		}
		byte cs_ = 0;
		for(int i =6;i < (bf.capacity()-2);i++){
			cs_ += bf.get(i);
		}
		bf.put(cs_);
		bf.put((byte) 0x16);
		this.frame = bf.array();
	}
	
	public static void main(String[] args) {
		byte[] addr = new byte[5];
		for(int i=0;i < 5;i++){
			addr[i] = (byte) i;
		}
		Frame f = new Frame(0, (byte)0x10, (byte)0x00, (byte)0x60, (byte)0x01, addr, new byte[0]);
		byte[] ff = f.getFrame();
		
		for(int i=0;i < ff.length;i++){
			System.out.println(Integer.toHexString(ff[i]&0xFF));
		}
	}
	
	public static boolean checkFrame(byte[] frame){
		byte cs = 0;
		for(int i =6;i < (frame.length-2);i++){
			cs += frame[i];
		}
		if(cs == frame[frame.length-2] && frame[frame.length - 1]==0x16){
			return true;
		}
		return false;
	}
	
}
