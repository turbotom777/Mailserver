package com.toms.mailserver.pop3.command;

import java.util.Iterator;
import java.util.List;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.Pop3Context;

public class ListCmd implements Pop3Cmd {
	public static final String CMD = "LIST";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception {
		if (ctx.getUser() == null || ctx.getPassword() == null){
			return "-ERR no user or password specified";
		}
		StringBuffer buf = new StringBuffer();
		List msgList = repository.getMessages(ctx.getUser());
		buf.append("+OK mailbox has " + msgList.size() + " messages" + Pop3Cmd.NL);
		int idx = 1;
		for (Iterator it = msgList.iterator(); it.hasNext(); ){
			Message msg = (Message) it.next();
			buf.append(idx + " " + msg.getSize() + Pop3Cmd.NL);
		}
		buf.append("."); 
		return buf.toString();
	}
}
