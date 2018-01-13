package org.fictio.askPro.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 使用google.common.cache包创建的内存缓存管理对象
 * @author dk
 */
public class CacheManager {
	
	private static Cache<String, String> localCache = CacheBuilder.newBuilder()
			.concurrencyLevel(4).expireAfterAccess(1000, TimeUnit.SECONDS)
			.maximumSize(1000).build();
	
	private static CacheManager cacheInstance = new CacheManager();
	
	private CacheManager(){};
	
	public static CacheManager getCacheInstance(){
		return cacheInstance;
	}
	
	public void setValue(String key,String value){
		localCache.put(key, value);
	}
	
	public String getValue(String key){
		return localCache.getIfPresent(key);
	}

}
