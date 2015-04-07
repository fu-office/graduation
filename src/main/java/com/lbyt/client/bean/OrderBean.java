package com.lbyt.client.bean;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderBean extends  JsonBean  {
	
	private static final long serialVersionUID = 3539665736716645074L;
	
	private Integer id;
	
	private Integer clientId;
	
	private String phone;
	
	private String name;
	
	private Date date;
	
	private String status;
	
	private String address;
	
	private Date deliveryTime;
	
	private String payStatus;
	
	private String payMethod;

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

	public String getPhone() {
		return phone;
	}
	
	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	@JsonProperty("deliveryTime")
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	@JsonProperty("payStatus")
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayMethod() {
		return payMethod;
	}

	@JsonProperty("payMethod")
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

}
