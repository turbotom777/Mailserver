package com.toms.mailserver.smtp.command;

import com.toms.mailserver.Message;

public class Quit implements SmtpCmd {
	public static final String CMD = "QUIT";
	
	public String getReplay() {
		return "221 See you later";
	}

	public boolean endOfCmd(){
		return true;
	}
	public void setData(String str) {	
	}

	public void setMessage(Message msg) {
	}
}
