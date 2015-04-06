package com.lbyt.client.bean;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class GiftBean extends  JsonBean {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer clientId;
	
	private String name;
	
	private String phone;
	
	private Date date;

	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	@JsonProperty("clientId")
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}

}
