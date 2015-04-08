package com.lbyt.client.bean;

public class AddressBean extends JsonBean {

	private static final long serialVersionUID = 3286060552538543743L;
	
	private String area;
	
	private String department;
	
	private String floor;
	
	private String room;

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
	
} 