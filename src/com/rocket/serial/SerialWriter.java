package com.rocket.serial;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingDeque;

public class SerialWriter implements Runnable{

	public static LinkedBlockingDeque queue_out = new LinkedBlockingDeque();
	OutputStream out;

	public SerialWriter(OutputStream out) {
		super();
		this.out = out;
	}

	@Override
	public void run() {
		while(true){
			try {
				byte[] out_buffer = (byte[]) queue_out.take();
				out.write(out_buffer);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
