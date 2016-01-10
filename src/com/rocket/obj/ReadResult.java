package com.rocket.obj;

public class ReadResult {
	private String read;  //表读数
	private String meterstatus;  //表状态
	private String valvestatus;  //阀门状态
	private String error;
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getMeterstatus() {
		return meterstatus;
	}
	public void setMeterstatus(String meterstatus) {
		this.meterstatus = meterstatus;
	}
	public String getValvestatus() {
		return valvestatus;
	}
	public void setValvestatus(String valvestatus) {
		this.valvestatus = valvestatus;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
