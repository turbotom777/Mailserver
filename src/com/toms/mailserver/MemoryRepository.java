package com.toms.mailserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemoryRepository implements MailRepository {
	
	Map userMessageLookup = new HashMap();
	
	MemoryRepository(){
	}
	
	public void addMessage(Message msg){
		synchronized(this.userMessageLookup){
			String key = msg.getRcpt().trim();
			int idx = key.indexOf("<");
			if (idx >= 0) {
				key = key.substring(idx + 1, key.length());
			}
			idx = key.indexOf(">");
			if (idx >= 0) {
				key = key.substring(0, idx);
			}
			List msgList =(List) this.userMessageLookup.get(key);
			if (msgList == null) {
				msgList = new ArrayList();
				this.userMessageLookup.put(key, msgList);
			}
			msgList.add(msg);
		}
	}
	
	public List getMessages(String user){
		String key = user.trim();
		List msgList =(List) this.userMessageLookup.get(key);
		if (msgList == null) {
			return new ArrayList();
		}
		return msgList;
	}
	
	public void remove(String user, int idx) throws Exception {
		synchronized(this.userMessageLookup){
			String key = user.trim();
			List msgList =(List) this.userMessageLookup.get(key);
			if (msgList == null) {
				throw new Exception("no messages for user available!");
			}
			if (msgList.size() < idx) {
				throw new Exception("Message " + idx + " not available!");
			}
			msgList.remove(idx);
		}
	}
	
}
