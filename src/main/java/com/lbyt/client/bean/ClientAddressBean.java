package com.lbyt.client.bean;

public class ClientAddressBean extends JsonBean {

	private static final long serialVersionUID = 3286060552538543743L;
	
	private Integer id;
	
	private String area;
	
	private String department;
	
	private String floor;
	
	private String room;
	
	private ClientBean client = new ClientBean();

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
} 