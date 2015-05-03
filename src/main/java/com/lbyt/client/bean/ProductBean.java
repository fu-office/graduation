package com.lbyt.client.bean;

import java.util.Date;

public class ProductBean extends JsonBean{

	private static final long serialVersionUID = -2089552334636780023L;
	
	private Integer id;
	
	private String productName;
	
	private Float price;
	
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
