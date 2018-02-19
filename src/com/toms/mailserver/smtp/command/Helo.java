package com.toms.mailserver.smtp.command;

import com.toms.mailserver.Message;

public class Helo implements SmtpCmd {
	public static final String CMD = "HELO";
	
	public String getReplay() {
		return "250 com.toms.mailserver, nice to meet you";
	}

	public boolean endOfCmd(){
		return true;
	}
	public void setData(String str) {	
	}

	public void setMessage(Message msg) {
	}
}
