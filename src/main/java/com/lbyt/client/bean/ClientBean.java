package com.lbyt.client.bean;

import java.util.Date;


public class ClientBean extends JsonBean {

	private static final long serialVersionUID = -860283441337862926L;

	private Integer id;
	
	private String registName;
	
	private String address;
	
	private String phone;
	
	private String password;
	
	private Date registDate;
	
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegistName() {
		return registName;
	}

	public void setRegistName(String registName) {
		this.registName = registName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
