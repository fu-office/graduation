package com.lbyt.client.bean;


public class Location extends JsonBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015030240967286242L;
	
	private int provinceId;
	
	private String provinceName;
	
	private int provinceLevel;
	
	private int parentId = -1;
	
	private String remark;

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getProvinceLevel() {
		return provinceLevel;
	}

	public void setProvinceLevel(int provinceLevel) {
		this.provinceLevel = provinceLevel;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
