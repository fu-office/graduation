package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockOrderSearchBean extends BaseSearchBean {
	private static final long serialVersionUID = -6351982586972497937L;

	private List<StockOrderBean> list = new ArrayList<StockOrderBean>();
	
	private Integer id;
	
	private Integer productId;
	
	private String productName;
	
	private Integer num;
	
	private Integer stockId;
	
	private Date createDate;
	
	private String stockType;

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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<StockOrderBean> getList() {
		return list;
	}

	public void setList(List<StockOrderBean> list) {
		this.list = list;
	}
}
