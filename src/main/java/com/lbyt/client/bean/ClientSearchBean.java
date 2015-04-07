package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.List;

public class ClientSearchBean extends BaseSearchBean{
	private static final long serialVersionUID = 4420381041967770966L;
	
	private List<ClientBean> list = new ArrayList<ClientBean>();
	
	private String registName;
	
	private Integer id;

	public List<ClientBean> getList() {
		return list;
	}

	public void setList(List<ClientBean> list) {
		this.list = list;
	}

	public String getRegistName() {
		return registName;
	}

	public void setRegistName(String registName) {
		this.registName = registName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}