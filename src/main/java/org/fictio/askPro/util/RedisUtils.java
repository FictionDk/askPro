package org.fictio.askPro.util;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
	@SuppressWarnings("unused")
	@Autowired
    private HashOperations<String, String, Object> hashOperations;
	@Autowired
    private ListOperations<String, Object> listOperations;
    @SuppressWarnings("unused")
	@Autowired
    private SetOperations<String, Object> setOperations;
    @SuppressWarnings("unused")
	@Autowired
    private ZSetOperations<String, Object> zSetOperations;
    
    public enum RedisKey{
    	USER_TOKEN_
    }
    
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /** 半天时间,排队使用,单位: 秒 */
    public final static long HALF_DAY = 60 * 60 * 12;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    private final static Gson gson = new Gson();

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }
    
	public void pushSet(String key, Object obj, long expire) {
    	listOperations.leftPush(key, gson.toJson(obj));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
	}

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }
    
    public List<Object> popGet(String key) {
    	return listOperations.range(key,0,listOperations.size(key));
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
    
    public void deleteKeys(String preKey) {
    	Set<String>keySet = redisTemplate.keys(preKey);
        if(keySet == null || keySet.size()<=0){  
            return;  
        }  
        redisTemplate.delete(keySet);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
}
