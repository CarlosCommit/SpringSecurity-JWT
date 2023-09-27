package com.automar.revista.exceptions;



public class UsernameDuplicated extends RuntimeException{

	/**
	 * 
	 */
	private String message; 
	private static final long serialVersionUID = 1L;
	
	public UsernameDuplicated(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
