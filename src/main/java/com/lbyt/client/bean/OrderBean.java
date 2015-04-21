package com.lbyt.client.bean;

import java.util.Date;

import com.lbyt.client.enums.OrderPayMethodEnum;
import com.lbyt.client.enums.OrderPayStatusEnum;
import com.lbyt.client.enums.OrderStatusEnum;

public class OrderBean extends  JsonBean  {
	
	private static final long serialVersionUID = 3539665736716645074L;
	
	private Integer id;
	
	private Integer clientId;
	
	private String phone;
	
	private String name;
	
	private Date date = new Date();
	
	private String status = OrderStatusEnum.UNORDER.toString();
	
	private String address;
	
	private String deliveryTime;
	
	private Date deliveryDate;
	
	private String payStatus = OrderPayStatusEnum.UNPAY.toString();
	
	private String payMethod = OrderPayMethodEnum.ONLINE.toString();
	
	private String area;
	
	private String floor;
	
	private String department;
	
	private String room;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
}
