package com.lbyt.client.bean;

public class StockBean extends JsonBean {
	private static final long serialVersionUID = -6351982586972497937L;

	private Integer id;
	
	private Integer prodcutId;
	
	private String productName;
	
	private Integer number;

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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
