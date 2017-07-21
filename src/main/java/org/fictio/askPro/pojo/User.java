package org.fictio.askPro.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class User {
    private Integer userId;

	@NotNull(message="用户名不能为空")
    private String userName;

    private String password;

    private String userEmail;
	@NotNull(message="手机号不能为空")
    private String userMobile;

    private String userRealName;

    private String userStatus;

    private Integer userScore;

    private String userAttention;

    private String userBlackList;

    private Date createTime;

    private Date lastLoginTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName == null ? null : userRealName.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getUserAttention() {
        return userAttention;
    }

    public void setUserAttention(String userAttention) {
        this.userAttention = userAttention == null ? null : userAttention.trim();
    }

    public String getUserBlackList() {
        return userBlackList;
    }

    public void setUserBlackList(String userBlackList) {
        this.userBlackList = userBlackList == null ? null : userBlackList.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userEmail="
				+ userEmail + ", userMobile=" + userMobile + ", userRealName=" + userRealName + ", userStatus="
				+ userStatus + ", userScore=" + userScore + ", userAttention=" + userAttention + ", userBlackList="
				+ userBlackList + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + "]";
	}
}