package com.lbyt.client.bean;

import com.lbyt.client.constant.CommConstants;

public class BaseSearchBean extends JsonBean {

	private static final long serialVersionUID = 2681443461936932034L;

	private int pageSize = CommConstants.PAGE_SIZE;
	
	private int pageNumber = CommConstants.PAGE_NUMBER;
	
	private int totalPages;
	
	private long count;

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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
}
