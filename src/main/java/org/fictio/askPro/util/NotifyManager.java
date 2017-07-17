package org.fictio.askPro.util;

public interface NotifyManager {
	public <T> void sendNotify(String mobile,T msg,String msgType);
}
