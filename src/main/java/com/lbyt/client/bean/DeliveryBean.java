package com.lbyt.client.bean;

public class DeliveryBean extends JsonBean{

	private static final long serialVersionUID = -5534614015430191533L;
	
	private Integer id;
	
	private String sex;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
