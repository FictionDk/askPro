package org.fictio.askPro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认设置的假设短信发送对象
 * @author dk
 */
public class DefaultNotifyClient implements NotifyManager {
	private static Logger log = LoggerFactory.getLogger(DefaultNotifyClient.class);
	public DefaultNotifyClient(){
		log.info("生成默认通知发送客户端");
	}
	public <T> void send(String mobile,T msg,String msgType){
		log.info(mobile+"|"+msg.toString());
	}
	@Override
	public <T> void sendNotify(String mobile, T msg, String msgType) {
		send(mobile,msg,msgType);
	}
}
