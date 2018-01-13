package org.fictio.askPro.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.fictio.askPro.aop.annotation.UserAccess;
import org.fictio.askPro.pojo.RequestData;
import org.fictio.askPro.util.TokenException;
import org.fictio.askPro.util.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Aspect
@Component
public class SecurityAspect {
	
	private static Logger log = LoggerFactory.getLogger(SecurityAspect.class);
	
	@Autowired
	private TokenManager tokenManger;
	
	@SuppressWarnings("unchecked")
	@Before(("@annotation(tokenCheck)"))
	public void testCheck(JoinPoint jp,UserAccess tokenCheck) throws Exception{
		log.info(jp.getArgs()[0].getClass().getName());
		RequestData<String> req = (RequestData<String>) jp.getArgs()[0];
		String userName = tokenManger.getTokenValue(req.getToken());
		req.setUserName(userName);
		log.info("tokenCheck:"+req.getToken()+"|"+userName);
		if(Strings.isNullOrEmpty(userName)){
			throw new TokenException();
		}
	}

}
