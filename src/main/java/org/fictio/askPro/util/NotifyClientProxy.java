package org.fictio.askPro.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 短信发送的动态代理对象
 * @author dk
 */
public class NotifyClientProxy implements InvocationHandler {

	private NotifyManager notifyClient;
	
	public Object createInstance(){
		notifyClient = getSuitableClient();
		return Proxy.newProxyInstance(notifyClient.getClass().getClassLoader()
				, notifyClient.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = method.invoke(notifyClient, args);
		return ret;
	}
	
	private NotifyManager getSuitableClient(){
		return new DefaultNotifyClient();
	}

}
