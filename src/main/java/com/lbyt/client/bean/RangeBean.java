package com.lbyt.client.bean;


public class RangeBean extends JsonBean {
	
	private static final long serialVersionUID = 5813310136567426969L;

	private Integer id;
	
	private String name;
	
	private Integer parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}
	
}
