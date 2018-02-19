package com.toms.mailserver.smtp.command;

import com.toms.mailserver.Message;

public interface SmtpCmd {
	public static final String NL = "\r\n";
	public void setMessage(Message msg);
	public void setData(String str);
	public String getReplay();
	public boolean endOfCmd();
}
