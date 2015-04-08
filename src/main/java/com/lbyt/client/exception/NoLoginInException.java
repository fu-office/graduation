package com.lbyt.client.exception;


public class NoLoginInException extends WaterException {

	private static final long serialVersionUID = 1992105018404606725L;
	
	public NoLoginInException(){
		super();
	}
	
	public NoLoginInException(String message){
		super(message);
	}

}
