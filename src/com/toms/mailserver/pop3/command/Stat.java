package com.toms.mailserver.pop3.command;

import java.util.Iterator;
import java.util.List;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.Pop3Context;

public class Stat implements Pop3Cmd {
	public static final String CMD = "STAT";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception {
		if (ctx.getUser() == null || ctx.getPassword() == null){
			return "-ERR no user or password specified";
		}
		List msgList = repository.getMessages(ctx.getUser());
		int size = 0;
		for (Iterator it = msgList.iterator(); it.hasNext(); ){
			Message msg = (Message) it.next();
			size += msg.getSize();
		}
		return "+OK " + msgList.size() + " " + size;
	}
}
