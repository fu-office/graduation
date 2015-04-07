package com.lbyt.client.error;

import com.lbyt.client.bean.JsonBean;

public class ErrorBean extends JsonBean{
	private static final long serialVersionUID = 1L;

	private String message;
	
	private String errorCode;
	
	public ErrorBean(){}
	
	public ErrorBean(String message, String code){
		this.message = message;
		this.errorCode = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}
	
	public static ErrorBean getInstance(){
		return new ErrorBean();
	}
	
}
