package com.toms.mailserver.smtp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.smtp.command.Quit;
import com.toms.mailserver.smtp.command.SmtpCmd;

public class SmtpThread extends Thread {

	private Socket socket;
	public static final String ENCODING = "US-ASCII";
	private MailRepository repository;

	public SmtpThread(Socket socket, MailRepository repository){
		this.socket = socket;
		this.repository = repository;
	}


	public void run(){
		InputStream in;                                  
		OutputStream out;   
		Message msg = new Message();
		try {
			//this.socket.setSoTimeout(5000);
		    in = this.socket.getInputStream();          
		    out = this.socket.getOutputStream();  
		
		    this.write(out, "220 com.toms.mailserver SMTP Mailserver ready");
		    
		    while ( true ){
		    	String cmdLine = this.read(in);
			    StringTokenizer token = new StringTokenizer(cmdLine.toString(), " ");
			    SmtpCmd action = null;
			    if (token.hasMoreTokens()){
			    	String cmd = token.nextToken().trim();
			    	action = SmtpServer.getCmdInstance(cmd);
			    }
			    if (action == null){
			    	this.write(out, "500 fatal error");
				    continue;
			    } else if (action.getClass().equals(Quit.class)){
			    	String replay = action.getReplay();
			    	this.write(out, replay);
			    	break;
			    }
			    action.setMessage(msg);
			    action.setData(cmdLine);
			    String replay = action.getReplay();
			    this.write(out, replay);
			    
			    while (!action.endOfCmd()){
			    	cmdLine = this.read(in);
			    	action.setData(cmdLine);
			    	replay = action.getReplay();
			    	if (replay != null){
			    		this.write(out, replay);
			    	}
			    }
		    }
		    this.repository.addMessage(msg);
		} catch(SocketException e) {
	    	e.printStackTrace();	
		} catch(IOException e) {
	    	e.printStackTrace();
	    } catch(Exception e) {
	    	e.printStackTrace();	
		} finally {
			try {
	    		this.socket.close();
	    	} catch (IOException ioe){
	    		ioe.printStackTrace();
	    	}
		}                   
	}
	
	public void write(OutputStream out, String write) throws IOException {
		if (!write.trim().endsWith("\n")){
			write += SmtpCmd.NL;
		}
		out.write(write.getBytes(ENCODING)); 
		out.flush();
		System.out.print("<" + write);
	}
	
	
	public String read(InputStream in) throws SocketException, IOException {
	    StringBuffer cmdBuf = new StringBuffer();
	    int i = 0;
	    int available = in.available();
	    while (available > 0){
	    	byte [] inData = new byte[available];
	    	int size = in.read(inData, 0, available);
	    	String str = new String(inData, 0, size, ENCODING);
	    	i = str.indexOf("\n");
	    	cmdBuf.append(str);
	    	available = in.available();
	    	if (size < 0){
	    		break;
	    	}
	    }
	    System.out.print(">" + cmdBuf.toString());
	    return cmdBuf.toString();
	}
}
