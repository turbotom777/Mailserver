package com.toms.mailserver.smtp.command;

import java.util.StringTokenizer;

import com.toms.mailserver.Message;

public class Rcpt implements SmtpCmd {
	public static final String CMD = "RCPT";
	private Message msg;
	private String replay;
	
	public String getReplay() {
		return this.replay;
	}

	public boolean endOfCmd(){
		return true;
	}
	public void setData(String str) {
		StringTokenizer token = new StringTokenizer(str, ":");
		if (token.countTokens() < 2){
			this.replay = "500 unknown error";
		}
		String cmd = token.nextToken();
		String rcpt = token.nextToken();
		this.msg.setRcpt(rcpt);
		this.replay = "250 ok";
		
	}

	public void setMessage(Message msg) {
		this.msg = msg;
	}
}
