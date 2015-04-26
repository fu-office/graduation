package com.lbyt.client.bean;

import java.util.Date;

public class StockOrderBean extends JsonBean {
	private static final long serialVersionUID = -6351982586972497937L;

	private Integer id;
	
	private Integer prodcutId;
	
	private String productName;
	
	private Integer num;
	
	private Integer stockId;
	
	private Date createDate;
	
	private String stockType;

	public Integer getProdcutId() {
		return prodcutId;
	}

	public void setProdcutId(Integer prodcutId) {
		this.prodcutId = prodcutId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
}
