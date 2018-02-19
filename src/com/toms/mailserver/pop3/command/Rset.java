package com.toms.mailserver.pop3.command;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.Pop3Context;

public class Rset implements Pop3Cmd {
	public static final String CMD = "RSET";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) {
		// TODO Auto-generated method stub
		return "+OK";
	}
}
