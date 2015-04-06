package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class AreaSearchBean extends BaseSearchBean {

	private static final long serialVersionUID = 7356428584760395928L;

	private List<AreaBean> list = new ArrayList<AreaBean>();
	
	private Integer id;
	
	private String prov;
	
	private String city;
	
	private String detail;
	
	private String shopState;
	
	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Date date;

	public List<AreaBean> getList() {
		return list;
	}

	@JsonProperty("list")
	public void setList(List<AreaBean> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopState() {
		return shopState;
	}

	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	
}
