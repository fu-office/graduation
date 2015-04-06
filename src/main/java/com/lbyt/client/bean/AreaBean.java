package com.lbyt.client.bean;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class AreaBean extends  JsonBean  {
	
	private static final long serialVersionUID = 3539665736716645074L;
	
	private Integer id;
	
	private String city;
	
	private String prov;
	
	private String detail;
	
	private Date date;
	
	private String shopState;

	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	public String getProv() {
		return prov;
	}

	@JsonProperty("prov")
	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getDetail() {
		return detail;
	}

	@JsonProperty("detail")
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}

	public String getShopState() {
		return shopState;
	}

	@JsonProperty("shopState")
	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	
}
