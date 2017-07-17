package org.fictio.askPro.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
public class TokenManager {

	private static TokenManager instance = new TokenManager();
	
	private static Cache<String, String> tokenCache = CacheBuilder.newBuilder()
			.concurrencyLevel(4).expireAfterAccess(6000, TimeUnit.SECONDS)
			.maximumSize(3000).build();
	
	private TokenManager(){};
	
	public static TokenManager getInstance(){
		return instance;
	}
	
	public String createToken(String userName){
		String token = UUID.randomUUID().toString();
		tokenCache.put(token, userName);
		return token;
	}
	
	public String getTokenValue(String token){
		String userName = tokenCache.getIfPresent(token);
		return userName;
	}

}
