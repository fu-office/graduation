package com.lbyt.client.bean;

public class PageBean extends JsonBean{
	private static final long serialVersionUID = 1L;

	private int count;
	
	private int pageSize;
	
	private int pageNumber;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
