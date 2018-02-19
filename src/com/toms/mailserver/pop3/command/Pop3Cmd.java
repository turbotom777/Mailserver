package com.toms.mailserver.pop3.command;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.Pop3Context;

public interface Pop3Cmd {
	public static final String NL = "\r\n";
	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception ;
	
}
