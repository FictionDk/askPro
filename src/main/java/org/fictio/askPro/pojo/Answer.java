package org.fictio.askPro.pojo;

import java.util.Date;

public class Answer {
    private Integer answerId;

    private Integer questionId;

    private Integer userId;
    
    private String userName;

    private Date answerCreateTime;

    private Date answerUpdateTime;

    private Integer commendCount;

    private String answerContent;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAnswerCreateTime() {
        return answerCreateTime;
    }

    public void setAnswerCreateTime(Date answerCreateTime) {
        this.answerCreateTime = answerCreateTime;
    }

    public Date getAnswerUpdateTime() {
        return answerUpdateTime;
    }

    public void setAnswerUpdateTime(Date answerUpdateTime) {
        this.answerUpdateTime = answerUpdateTime;
    }

    public Integer getCommendCount() {
        return commendCount;
    }

    public void setCommendCount(Integer commendCount) {
        this.commendCount = commendCount;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent == null ? null : answerContent.trim();
    }
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", questionId=" + questionId + ", userId=" + userId + ", userName="
				+ userName + ", answerUpdateTime=" + answerUpdateTime + ", commendCount=" + commendCount + "]";
	}

}