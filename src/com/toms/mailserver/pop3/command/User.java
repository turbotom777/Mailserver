package com.toms.mailserver.pop3.command;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.Pop3Context;

public class User implements Pop3Cmd {
	public static final String CMD = "USER";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) {
		ctx.setUser(params.trim());
		return "+OK Please enter password";
	}
}
