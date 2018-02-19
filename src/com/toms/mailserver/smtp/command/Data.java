package com.toms.mailserver.smtp.command;

import java.util.StringTokenizer;

import com.toms.mailserver.Message;

public class Data implements SmtpCmd {
	public static final String CMD = "DATA";
	private Message msg;
	private String replay;
	private boolean bEndofComd = false;
	
	public String getReplay() {
		return this.replay;
	}
	
	public boolean endOfCmd(){
		return this.bEndofComd;
	}

	public void setData(String str) {
		if (str.trim().startsWith(CMD)){
			this.replay = "354 End data with <CR><LF>.<CR><LF>";
			return;
		}
		this.msg.appendData(str);
		// TODO!
		if (this.msg.getData().endsWith("\r\n.\r\n")){
			this.replay = "250 Message accepted for delivery";
			this.bEndofComd = true;
		} else {
			this.bEndofComd = false;
			this.replay = null;
		}
	}

	public void setMessage(Message msg) {
		this.msg = msg;
	}
}
