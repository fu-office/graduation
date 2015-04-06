package com.lbyt.client.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area")
public class AreaEntity implements Serializable{

	private static final long serialVersionUID = 2232036685062083259L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "long_name")
	private String detail;
	
	@Column(name = "city_name")
	private String city;
	
	@Column(name = "prov_name")
	private String prov;
	
	@Column(name = "create_date")
	private Date date;
	
	@Column(name = "shop_state")
	private String shopState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getShopState() {
		return shopState;
	}

	public void setShopState(String shopState) {
		this.shopState = shopState;
	}
	
}
