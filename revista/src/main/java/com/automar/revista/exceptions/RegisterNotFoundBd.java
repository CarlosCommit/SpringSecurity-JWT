package com.automar.revista.exceptions;

public class RegisterNotFoundBd extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message; 
	public RegisterNotFoundBd(String message)
	{
		super(); 
		this.message = message; 
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
