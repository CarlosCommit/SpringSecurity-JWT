package com.automar.revista.exceptions;

public class RoleNotFound extends RuntimeException{

	/**
	 * 
	 */
	private String message; 
	private static final long serialVersionUID = 1L;

	public RoleNotFound(String message)
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
