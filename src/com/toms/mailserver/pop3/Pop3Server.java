package com.toms.mailserver.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.pop3.command.Dele;
import com.toms.mailserver.pop3.command.ListCmd;
import com.toms.mailserver.pop3.command.Noop;
import com.toms.mailserver.pop3.command.Pass;
import com.toms.mailserver.pop3.command.Pop3Cmd;
import com.toms.mailserver.pop3.command.Quit;
import com.toms.mailserver.pop3.command.Retr;
import com.toms.mailserver.pop3.command.Rset;
import com.toms.mailserver.pop3.command.Stat;
import com.toms.mailserver.pop3.command.Top;
import com.toms.mailserver.pop3.command.User;


/* do not implement Runnable or extend Thread since the pop server is the main thread */
public class Pop3Server {
	private final int POP3_PORT = 110;
	
	private MailRepository repository;
	public static Map registeredCommands;
	
	public Pop3Server(MailRepository repository){
		this.repository = repository;
		Pop3Server.registerCommands();
	}
	
	private static void registerCommands(){
		if (Pop3Server.registeredCommands == null){
			Pop3Server.registeredCommands = new HashMap();
			Pop3Server.registeredCommands.put(Dele.CMD, Dele.class);
			Pop3Server.registeredCommands.put(ListCmd.CMD, ListCmd.class);
			Pop3Server.registeredCommands.put(Noop.CMD, Noop.class);
			Pop3Server.registeredCommands.put(Pass.CMD, Pass.class);
			Pop3Server.registeredCommands.put(Quit.CMD, Quit.class);
			Pop3Server.registeredCommands.put(Retr.CMD, Retr.class);
			Pop3Server.registeredCommands.put(Rset.CMD, Rset.class);
			Pop3Server.registeredCommands.put(Stat.CMD, Stat.class);
			Pop3Server.registeredCommands.put(User.CMD, User.class);
			Pop3Server.registeredCommands.put(Top.CMD,Top.class);
		}
	}
	
	public static Pop3Cmd getCmdInstance(String cmd){
		Class clazz = (Class) Pop3Server.registeredCommands.get(cmd);
    
		Pop3Cmd action = null; 
	    if (clazz != null){
		    try {
		    	action = (Pop3Cmd) clazz.newInstance();
		    } catch (InstantiationException ie){
		    	ie.printStackTrace();
		    } catch (IllegalAccessException iae){
		    	iae.printStackTrace();
		    }		    	
	    }
	    return action;
	}
	
	public void run (){
		ServerSocket pop3Listener;
		try {
			pop3Listener = new ServerSocket(POP3_PORT);
		} catch (IOException e){
			e.printStackTrace();
			return;
		}
		Socket socket = null;
		System.out.println("OK pop3 Server toms ready, waiting for delivering Mails...");
		while(true){
			try {
				socket = pop3Listener.accept();
			} catch (IOException e){
				e.printStackTrace();
			}
			if (socket != null){
				Pop3Thread thread = new Pop3Thread(socket, repository);
				thread.start();
			}
		}
	}
}
