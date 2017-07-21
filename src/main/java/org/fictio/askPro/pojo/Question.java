package org.fictio.askPro.pojo;

import java.util.Date;

public class Question {
    private Integer questId;

    private Integer questUserId;

    private String questTitle;

    private Date questCreateTime;

    private Date questUpdateTime;

    private String questBody;

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getQuestUserId() {
        return questUserId;
    }

    public void setQuestUserId(Integer questUserId) {
        this.questUserId = questUserId;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle == null ? null : questTitle.trim();
    }

    public Date getQuestCreateTime() {
        return questCreateTime;
    }

    public void setQuestCreateTime(Date questCreateTime) {
        this.questCreateTime = questCreateTime;
    }

    public Date getQuestUpdateTime() {
        return questUpdateTime;
    }

    public void setQuestUpdateTime(Date questUpdateTime) {
        this.questUpdateTime = questUpdateTime;
    }

    public String getQuestBody() {
        return questBody;
    }

    public void setQuestBody(String questBody) {
        this.questBody = questBody == null ? null : questBody.trim();
    }

	@Override
	public String toString() {
		return "Question [questId=" + questId + ", questUserId=" + questUserId + ", questTitle=" + questTitle + "]";
	}
}