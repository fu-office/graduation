package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderSearchBean extends BaseSearchBean {

	private static final long serialVersionUID = 7356428584760395928L;

	private List<OrderBean> list = new ArrayList<OrderBean>();
	
	private Integer id;
	
	private String clientName;
	
	private String status;

	public List<OrderBean> getList() {
		return list;
	}

	@JsonProperty("list")
	public void setList(List<OrderBean> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}
	
}
