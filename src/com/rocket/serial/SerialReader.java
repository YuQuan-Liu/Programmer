package com.rocket.serial;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

import com.rocket.util.StringUtil;

public class SerialReader implements Runnable{
	
	public static LinkedBlockingDeque<byte[]> queue_in = new LinkedBlockingDeque<byte[]>();
	InputStream in;

	public SerialReader(InputStream in) {
		super();
		this.in = in;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[512];
		int len = -1;
		
		int start = 0;
		int copyed = 0;
		
		byte[] buffer_re = new byte[512];  //接收到的所有的数据的buffer
		int buffer_re_cnt = 0;     //接收到的字节个数
		byte[] frame = new byte[256];  //存放真正的帧的数据
		int buffer_frame_start = 0;  //buffer中frame开始的位置
		int frame_receive_cnt = 0;  //接收到的frame的字节数
		try {
			while(!Thread.interrupted()){
				while((len = in.read(buffer)) > 0){
//					System.out.println(len);
//					System.out.println(StringUtil.byteArrayToHexStr(buffer, len));
					if(start == 0){
						for(int i = 0;i < len;i++){
							buffer_re[buffer_re_cnt+i] = buffer[i];
						}
						buffer_re_cnt += len;
						for(int i = 0;i < buffer_re_cnt;i++){
							if(buffer_re[i] == 0x68 || buffer_re[i] == 0x0E){
								start = 1;
								frame_receive_cnt = 0;
								buffer_frame_start = i;
								break;
							}
						}
						if(start == 1){
							for(int i = buffer_frame_start;i < buffer_re_cnt;i++,frame_receive_cnt++){
								frame[i-buffer_frame_start] = buffer_re[i];
							}
							copyed = 1;
						}
						if(buffer_re_cnt >= 400){
							System.out.println("有400个数据还没有收到帧");
							buffer_re_cnt = 0;   //重新开始接收处理
							start = 0;
						}
					}
					
					if(start == 1){
						//开始接收帧
						if(copyed != 1){
							for(int i = 0;i < len;i++){
								frame[frame_receive_cnt+i] = buffer[i];
							}
							frame_receive_cnt += len;
						}
						copyed = 0;
						
//						System.out.println("Frame:"+StringUtil.byteArrayToHexStr(frame, frame_receive_cnt));
						
						switch (frame[0]){
						case 0x68:
							if(frame_receive_cnt >11){
								if(frame[5] == 0x68){
									//集中器
									int frame_len = 0;
									if(frame[1] == frame[3] && frame[2] == frame[4]){
										frame_len = (frame[1]&0xFF) | ((frame[2]&0xFF)<<8);
										frame_len = frame_len >> 2;
										frame_len = frame_len + 8;
										if(frame_receive_cnt >= frame_len){
											byte cs = 0;
											for(int i = 6;i < frame_len-2;i++){
												cs += frame[i];
											}
											if(cs == frame[frame_len-2] && frame[frame_len-1] == 0x16){
												//the frame is good;
												queue_in.put(Arrays.copyOf(frame, frame_len));
												System.out.println("Good:~~~"+StringUtil.byteArrayToHexStr(frame, frame_len));
											}else{
												//这一帧有错误  放弃
											}
											buffer_re_cnt = 0;   //重新开始接收处理
											start = 0;
										}
									}else{
										//帧错误
									}
								}else{
									//188表
									int frame_len = 0;
									frame_len = frame[10] + 13;
									if(frame_receive_cnt >= frame_len){
										byte cs = 0;
										for(int i = 0;i < frame_len-2;i++){
											cs += frame[i];
										}
										if(cs == frame[frame_len-2] && frame[frame_len-1] == 0x16){
											//the frame is good;
											queue_in.put(Arrays.copyOf(frame, frame_len));
											System.out.println("Good:~~~"+StringUtil.byteArrayToHexStr(frame, frame_len));
										}else{
											//这一帧有错误  放弃
										}
										buffer_re_cnt = 0;   //重新开始接收处理
										start = 0;
									}
								}
							}
							break;
						case 0x0E:
							if(frame[2] == 0x0B){
								//表
								if(frame_receive_cnt >= 9){
									//检测帧
									byte cs = 0;
									for(int i=0;i<9;i++){
										cs ^= frame[i];
									}
									if(cs == 0){
										//the frame is good 
										queue_in.put(Arrays.copyOf(frame, 9));
										System.out.println("Good:~~~"+StringUtil.byteArrayToHexStr(frame, 9));
									}
									buffer_re_cnt = 0;   //重新开始接收处理
									start = 0;
								}
							}
							if(frame[2] == 0x0A){
								//采集器
								if(frame_receive_cnt >= 10){
									//检测帧
									byte cs = 0;
									for(int i=0;i<10;i++){
										cs ^= frame[i];
									}
									if(cs == 0){
										//the frame is good 
										queue_in.put(Arrays.copyOf(frame, 10));
										System.out.println("Good:~~~"+StringUtil.byteArrayToHexStr(frame, 10));
									}
									buffer_re_cnt = 0;   //重新开始接收处理
									start = 0;
								}
							}
							
							break;
						}
					}
					
				}
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
