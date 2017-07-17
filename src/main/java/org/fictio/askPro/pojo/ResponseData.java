package org.fictio.askPro.pojo;

/**
 * 返回数据
 * @author dk
 * @param <T>
 *
 */
public class ResponseData<T> {
	
	private String code;
	private String result;
	private String message;
	private String token;
	
	private T object;
	
	public ResponseData(){
		this.code = "0";
		this.result = "false";
	}
	
	public void setSuccess(){
		this.code = "1";
		this.result = "success";
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
}
