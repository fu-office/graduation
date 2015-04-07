package com.lbyt.client.enums;

public enum OrderPayMethodEnum{
	// 货到付款
	LOCAL(0),
	// 在线支付
	ONLINE(1),
	// 赊账
	CREDIT(2);
	
	private int status;
	
	private OrderPayMethodEnum(int status) {
		this.status = status;
	}
	
	public String toString(){
		return String.valueOf(this.status);
	}
}