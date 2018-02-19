package com.toms.mailserver;


import com.toms.mailserver.pop3.Pop3Server;
import com.toms.mailserver.smtp.SmtpServer;

public class MailServer {
	
	public void init(){
		MailRepository repository = new MemoryRepository();
		SmtpServer smtpServer = new SmtpServer(repository);
		smtpServer.start();
		
		/* pop3 server runs within the main thread! */
		Pop3Server pop3Server = new Pop3Server(repository);
		pop3Server.run();
	}
	

	public static void main(String [] args){
		MailServer server = new MailServer();
		server.init();
		System.out.print("Exit");
	}
}
