package org.fictio.askPro.util;

import java.util.UUID;
import org.fictio.askPro.util.RedisUtils.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {
	
	@Autowired
	private RedisUtils redisUtils;
	
	
	public String createToken(String userName){
		String token = UUID.randomUUID().toString();
		redisUtils.set(RedisKey.USER_TOKEN_+token, userName, RedisUtils.HALF_DAY);
		return token;
	}
	
	public String getTokenValue(String token){
		return redisUtils.get(RedisKey.USER_TOKEN_+token);
	}

}
