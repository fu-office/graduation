package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GiftSearchBean extends BaseSearchBean {

	private static final long serialVersionUID = 3523545923427212698L;
	
	private List<GiftBean> list = new ArrayList<GiftBean>();
	
	private Date startDate;
	
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<GiftBean> getList() {
		return list;
	}

	public void setList(List<GiftBean> list) {
		this.list = list;
	}

}
