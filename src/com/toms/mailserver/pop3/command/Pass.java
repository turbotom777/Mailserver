package com.toms.mailserver.pop3.command;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.Pop3Context;

public class Pass implements Pop3Cmd {
	public static final String CMD = "PASS";
	
	public String getReplay(Pop3Context ctx, String params, MailRepository repository) {
		ctx.setPassword(params.trim());
		return "+OK mailbox locked and ready";
	}
}
