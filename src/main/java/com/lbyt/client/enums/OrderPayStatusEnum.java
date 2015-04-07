package com.lbyt.client.enums;

public enum OrderPayStatusEnum{
	// 未支付
	UNPAY(0),
	// 已支付
	PAID(1);
	
	private int status;
	
	private OrderPayStatusEnum(int status) {
		this.status = status;
	}
	
	public String toString(){
		return String.valueOf(this.status);
	}
}