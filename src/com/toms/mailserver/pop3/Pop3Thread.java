package com.toms.mailserver.pop3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.toms.mailserver.MailRepository;
import com.toms.mailserver.Message;
import com.toms.mailserver.pop3.command.Pop3Cmd;
import com.toms.mailserver.pop3.command.Quit;

public class Pop3Thread extends Thread {
	
	public static Map actions = new HashMap();
	private Socket socket;
	public static final String ENCODING = "US-ASCII";
	private MailRepository repository;

	public Pop3Thread(Socket socket, MailRepository repository){
		this.socket = socket;
		this.repository = repository;
	}

	
	public void run(){
		InputStream in;                                  
		OutputStream out;   
		try {
			//this.socket.setSoTimeout(5000);
		    in = this.socket.getInputStream();          
		    out = this.socket.getOutputStream();  
		
		    this.write(out, "+OK com.toms.mailserver POP3 Server ready");
		    
		    Pop3Context ctx = new Pop3Context();
		    
		    while ( true ){
		    	String cmdLine = this.read(in);
			    StringTokenizer token = new StringTokenizer(cmdLine.toString(), " ");
			    Pop3Cmd action = null;
			    if (token.hasMoreTokens()){
			    	String cmd = token.nextToken().trim();
			    	action = Pop3Server.getCmdInstance(cmd);
			    }
			    StringBuffer buf = new StringBuffer();
			    while (token.hasMoreTokens()){
			    	buf.append(token.nextToken() + " ");
			    }
			    if (action == null){
			    	this.write(out, "-ERR unknown command");
				    continue;
			    } else if (action.getClass().equals(Quit.class)){
			    	String replay = action.getReplay(ctx, null, this.repository);
			    	this.write(out, replay);
			    	break;
			    }
			    String replay = action.getReplay(ctx, buf.toString().trim(), this.repository);
			    this.write(out, replay);
		    }
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
			write += Pop3Cmd.NL;
		}
		out.write(write.getBytes(ENCODING)); 
		out.flush();
		System.out.print("<" + write);
	}
	
	
	public String read(InputStream in) throws SocketException, IOException {
	    StringBuffer cmdBuf = new StringBuffer();
	    int i = 0;
	    while (i <= 0){
	    	//int available = in.available();
	    	int available = 1024;
	    	byte [] inData = new byte[available];
	    	int size = in.read(inData, 0, available);
	    	if (size < 0){
	    		break;
	    	}
	    	String str = new String(inData, 0, size, ENCODING);
	    	i = str.indexOf("\n");
	    	cmdBuf.append(str);
	    }
	    System.out.print(">" + cmdBuf.toString());
	    return cmdBuf.toString();
	}
}
