package org.fictio.askPro.pojo;

public class RequestData<T> {
	private String token;
	private String code;
	private String userName;
	private T obj;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "RequestData [token=" + token + ", code=" + code + ", obj=" + obj.toString() + "]";
	}
}
