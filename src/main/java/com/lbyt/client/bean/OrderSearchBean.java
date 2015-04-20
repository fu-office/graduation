package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderSearchBean extends BaseSearchBean {

	private static final long serialVersionUID = 7356428584760395928L;

	private List<OrderBean> list = new ArrayList<OrderBean>();
	
	private Integer id;
	
	private String clientName;
	
	private String status;
	
	private String area;
	
	private Date createDate;
	
	private Integer clientId;
	
	public List<OrderBean> getList() {
		return list;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
	public void setClientId(Integer id) {
		this.clientId = id;
	}
	
	public Integer getClientId() {
		return clientId;
	}
	
}
