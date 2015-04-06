package com.lbyt.client.bean;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClientBean extends  JsonBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 595470872980813098L;
	
	private Date modifyDate;
	
	private Date registerDate;
	
	private Integer id;
	
	private String cardNum;
	
	private String address;
	
	private String name;
	
	private String phoneNumber;
	
	private String telNumber;
	
	private String postCode;
	
	private Date birthday;
	
	private String province;
	
	private String city;
	
	private String shopName;
	
	private String remark;
	
	private String shopState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShopName() {
		return shopName;
	}

	@JsonProperty("shopName")
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRemark() {
		return remark;
	}
	
	@JsonProperty("remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getShopState() {
		return shopState;
	}

	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	
}
