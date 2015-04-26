package com.lbyt.client.enums;

public enum StockTypeEnum {
	INSTOCK(0), OUTSTOCK(1);
	
	private int type;

	private StockTypeEnum(int type) {
		this.type = type;
	}
	
	public String toString(){
		return String.valueOf(type);
	}
}
