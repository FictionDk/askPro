package org.fictio.askPro.pojo;

import java.util.List;

public class Page<T> {
	private int pageStart = 1;
	//计划每页条数
	private int pageSize = 8;
	//当前条数
	private int countSize;
	private List<T> pageContent;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getPageContent() {
		return pageContent;
	}
	public void setPageContent(List<T> pageContent) {
		this.pageContent = pageContent;
	}
	public int getCountSize() {
		return countSize;
	}
	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
}
