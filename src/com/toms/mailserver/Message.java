package com.toms.mailserver;

public class Message {

	private String from;
	private String rcpt;
	private StringBuffer data = new StringBuffer();
	
	public String getData() {
		return data.toString();
	}
	public void setData(String data) {
		this.data = new StringBuffer(data);
	}
	public void appendData(String data) {
		this.data.append(data);
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getRcpt() {
		return rcpt;
	}
	public void setRcpt(String rcpt) {
		this.rcpt = rcpt;
	}
	public int getSize(){
		return this.data.length();
	}
}
