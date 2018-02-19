package com.toms.mailserver.pop3.command;

import java.util.List;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.Pop3Context;

public class Dele implements Pop3Cmd {
	public static final String CMD = "DELE";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception {
		if (ctx.getUser() == null || ctx.getPassword() == null){
			return "-ERR no user or password specified";
		}
		int idx = Integer.parseInt(params.trim());
		repository.remove(ctx.getUser(), idx-1);
		return "+OK message marked for delete";
	}
}

