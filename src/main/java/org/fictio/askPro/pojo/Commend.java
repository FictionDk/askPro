package org.fictio.askPro.pojo;

import java.util.Date;

public class Commend {
    private Integer commendId;

    private Integer answerId;

    private Integer userId;

    private String userName;

    private int commendStatus;

    private Date commendTime;

    public Integer getCommendId() {
        return commendId;
    }

    public void setCommendId(Integer commendId) {
        this.commendId = commendId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

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

    public int getCommendStatus() {
		return commendStatus;
	}

	public void setCommendStatus(int commendStatus) {
		this.commendStatus = commendStatus;
	}

	public Date getCommendTime() {
        return commendTime;
    }

    public void setCommendTime(Date commendTime) {
        this.commendTime = commendTime;
    }

	@Override
	public String toString() {
		return "Commend [commendId=" + commendId + ", answerId=" + answerId + ", userId=" + userId + ", userName="
				+ userName + ", commendStatus=" + commendStatus + ", commendTime=" + commendTime + "]";
	}
}