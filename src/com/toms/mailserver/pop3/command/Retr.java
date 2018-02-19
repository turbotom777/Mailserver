package com.toms.mailserver.pop3.command;

import java.util.List;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.Pop3Context;

public class Retr implements Pop3Cmd {
	public static final String CMD = "RETR";
	
	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception  {
		if (ctx.getUser() == null || ctx.getPassword() == null){
			return "-ERR no user or password specified";
		}
		int idx = Integer.parseInt(params.trim());
		List msgList = repository.getMessages(ctx.getUser());
		Message msg = (Message) msgList.get(idx - 1);
		String data = msg.getData();
		if (!data.endsWith(Pop3Cmd.NL + ".")){
			data = data + Pop3Cmd.NL + ".";
		}
		
		return "+OK message follows" + Pop3Cmd.NL +  data;
	}
}
