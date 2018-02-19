package com.toms.mailserver.pop3.command;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.Pop3Context;

public class Quit implements Pop3Cmd {
	public static final String CMD = "QUIT";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) {
		return "+OK bye";
	}
}
