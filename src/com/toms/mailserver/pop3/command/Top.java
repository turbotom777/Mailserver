package com.toms.mailserver.pop3.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.Pop3Context;

public class Top implements Pop3Cmd {
	public static final String CMD = "TOP";

	public String getReplay(Pop3Context ctx, String params, MailRepository repository) throws Exception {
		StringTokenizer token = new StringTokenizer(params, " ");
		if (token.countTokens() < 2){
			return "-ERR wrong cmd format";
		}

		String msgNb = token.nextToken();
		String lines = token.nextToken();
		
		if (ctx.getUser() == null || ctx.getPassword() == null){
			return "-ERR no user or password specified";
		}
		int idx = Integer.parseInt(msgNb.trim());
		List msgList = repository.getMessages(ctx.getUser());
		Message msg = (Message) msgList.get(idx - 1);
		String data = msg.getData();
		StringTokenizer token2 = new StringTokenizer(data, Pop3Cmd.NL);
		List lineList = new ArrayList();
		while(token2.hasMoreTokens()){
			lineList.add(token2.nextToken());
		}
		StringBuffer buf = new StringBuffer();
		int nbLines = Integer.parseInt(lines);
		
		if (lineList.size() < nbLines){
			nbLines = lineList.size();
		}
		if (nbLines == 0){
			nbLines = 2;
		}
		for (int i = 0; i < nbLines; i++){
			String line = (String)lineList.get(i);
			buf.append(line + NL);
		}
		
		if (!buf.toString().endsWith(Pop3Cmd.NL + ".")){
			buf.append(Pop3Cmd.NL + ".");
		}
		return "+OK retrieve " + lines + " of msg " + msgNb + NL + buf.toString();
	}
}
