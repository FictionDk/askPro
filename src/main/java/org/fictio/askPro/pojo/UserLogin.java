package org.fictio.askPro.pojo;

import javax.validation.constraints.NotNull;

/**
 * 登录校验使用的user
 * @author dk
 */
public class UserLogin {
    @NotNull(message="用户名不能为空")
	private String userName;
    @NotNull(message="密码不能为空")
	private String password;
    private String userMobile;
    private String userEmail;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
