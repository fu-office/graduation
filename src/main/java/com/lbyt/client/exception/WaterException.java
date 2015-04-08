package com.lbyt.client.exception;

import java.util.ArrayList;
import java.util.List;

import com.lbyt.client.error.ErrorBean;

public class WaterException extends RuntimeException {

	private static final long serialVersionUID = 4118518507428282600L;
	
	private List<ErrorBean> errors = new ArrayList<ErrorBean>();
	
	public WaterException(){
		super();
	}
	
	public WaterException(String message){
		super(message);
	}

	public List<ErrorBean> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorBean> errors) {
		this.errors = errors;
	}

}
