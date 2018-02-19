package com.toms.mailserver.smtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.smtp.command.Data;
import com.toms.mailserver.smtp.command.From;
import com.toms.mailserver.smtp.command.Helo;
import com.toms.mailserver.smtp.command.Quit;
import com.toms.mailserver.smtp.command.Rcpt;
import com.toms.mailserver.smtp.command.SmtpCmd;

public class SmtpServer extends Thread {

	private final int SMTP_PORT = 25;
	
	private MailRepository repository;
	public static Map registeredCommands;
	
	public SmtpServer(MailRepository repository){
		this.repository = repository;
		SmtpServer.registerCommands();
	}
	
	private static void registerCommands(){
		if (SmtpServer.registeredCommands == null){
			SmtpServer.registeredCommands = new HashMap();
			SmtpServer.registeredCommands.put(Helo.CMD, Helo.class);
			SmtpServer.registeredCommands.put(From.CMD, From.class);
			SmtpServer.registeredCommands.put(Rcpt.CMD, Rcpt.class);
			SmtpServer.registeredCommands.put(Data.CMD, Data.class);
			SmtpServer.registeredCommands.put(Quit.CMD, Quit.class);
		}
	}
	
	public static SmtpCmd getCmdInstance(String cmd){
		Class clazz = (Class) SmtpServer.registeredCommands.get(cmd);
    
	    SmtpCmd action = null; 
	    if (clazz != null){
		    try {
		    	action = (SmtpCmd) clazz.newInstance();
		    } catch (InstantiationException ie){
		    	ie.printStackTrace();
		    } catch (IllegalAccessException iae){
		    	iae.printStackTrace();
		    }		    	
	    }
	    return action;
	}
	
	public void run (){
		ServerSocket smtpListener;
		try {
			smtpListener = new ServerSocket(SMTP_PORT);
		} catch (IOException e){
			e.printStackTrace();
			return;
		}
		Socket socket = null;
		System.out.println("OK smtp Server toms ready, waiting for Mails...");
		while(true){
			try {
				socket = smtpListener.accept();
			} catch (IOException e){
				e.printStackTrace();
			}
			if (socket != null){
				Message msg = new Message();
				SmtpThread thread = new SmtpThread(socket, repository);
				thread.start();
			}
		}
	}
	
	

}
