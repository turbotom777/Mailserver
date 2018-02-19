package com.toms.mailserver;

import java.util.List;

public interface MailRepository {
	public List getMessages(String user) throws Exception;
	public void addMessage(Message msg) throws Exception;
	public void remove(String user, int idx) throws Exception;
}
