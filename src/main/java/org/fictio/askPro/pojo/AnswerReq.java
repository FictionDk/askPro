package org.fictio.askPro.pojo;

/**
 * 回答列表请求
 * @author dk
 */
public class AnswerReq {

	private int pageStart;
	private int questId;
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getQuestId() {
		return questId;
	}
	public void setQuestId(int questId) {
		this.questId = questId;
	}
	@Override
	public String toString() {
		return "AnswerReq [pageStart=" + pageStart + ", questId=" + questId + "]";
	}
}
